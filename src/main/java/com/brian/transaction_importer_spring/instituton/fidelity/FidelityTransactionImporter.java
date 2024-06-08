package com.brian.transaction_importer_spring.instituton.fidelity;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import com.brian.transaction_importer_spring.service.CategoryInfererService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
@Log4j2
@RequiredArgsConstructor
public class FidelityTransactionImporter {

    private final CategoryInfererService categoryInfererService;

    private final CategoryRepository categoryRepository;

    private final VendorRepository vendorRepository;

    private final AccountRepository accountRepository;

    private String findCurrencyToken(String[] description) {
        for(String word : description) {
            if (word.contains("$")) {
                return word;
            }
        }
        log.error("Could not find a currency token in description: {}", (Object) description);
        return null;
    }

    // Fidelity is super annoying. The actual body of the message isn't structured well at all.
    //   International transactions have a slightly different email template and has to be parsed differently.
    public Transaction handleTransaction(MailMessage mailMessage) {
        Transaction transaction = new Transaction();
        ArrayList<String> lines = parseMailMessage(mailMessage);

        String[] cardNumberSplit = lines.get(0).split(" ");
        String cardNumber = cardNumberSplit[cardNumberSplit.length - 1].strip();

        String joinedDescription = String.join(" ", lines.get(1), lines.get(2));
        String originalDescription = joinedDescription.split("\\. ")[0].strip();
        String[] tokens = originalDescription.strip().split(" ");
        String messageId = mailMessage.getHeaders().get("Message-ID");
        Timestamp date = new Timestamp(Long.parseLong(mailMessage.getHeaders().get("Custom-Epoch")) * 1000L);
        Double amount = Double.parseDouble(findCurrencyToken(tokens).replace("$", ""));  //TODO: Not super proud of this
        String merchant = originalDescription.split(" at ")[1];
        String category = categoryInfererService.getCategory(merchant);

        log.info("Card Number: {}", cardNumber);
        log.info("amount: {}", amount);
        log.info("date: {}", date);
        log.info("detail: {}\n", originalDescription);

        transaction.setDescription(merchant);
        transaction.setDate(date);
        transaction.setOriginalDescription(originalDescription);
        transaction.setAmount(amount);
        transaction.setTransactionType("debit");
        transaction.setMerchant(vendorRepository.findOrCreate(merchant));
        transaction.setCategory(categoryRepository.findByName(category));
        transaction.setAccount(accountRepository.findByAlias(cardNumber));
        transaction.setNotes("Fidelity");
        transaction.setMailMessageId(messageId);

        log.info(transaction);

        return transaction;
    }

    public ArrayList<String> parseMailMessage(MailMessage mailMessage) {
        String body = mailMessage.getBody();
        Document document = Jsoup.parse(body);
        String content = document.wholeText();
        content = content.replace("\n\n", "\n");
        String[] array = content.split("\n");

        ArrayList<String> result = new ArrayList<>();
        for (String s : array) {
            String element = s.trim();
            if (!element.equals("\n") && !element.isEmpty()) {
                result.add(element);
            }
        }

        return result;
    }

}
