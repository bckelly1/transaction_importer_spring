package com.brian.transaction_importer_spring.instituton;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import com.brian.transaction_importer_spring.service.CategoryInfererService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FidelityParser {
    @Autowired
    private CategoryInfererService categoryInfererService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private AccountRepository accountRepository;

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
        String body = mailMessage.getBody();
        String[] lines = body.split("\n");

        String[] cardNumberSplit = lines[0].split(" ");
        String cardNumber = cardNumberSplit[cardNumberSplit.length - 1].strip();

        String originalDescription = lines[1].split("\\. ")[0].strip();
        String[] tokens = originalDescription.strip().split(" ");
        Double amount = Double.parseDouble(findCurrencyToken(tokens).replace("$", ""));  //TODO: Not super proud of this
        String merchant = originalDescription.split(" at ")[1];
        String category = categoryInfererService.getCategory(merchant);

        log.info("Card Number: {}", cardNumber);
        log.info("amount: {}", amount);
        log.info("detail: {}\n", originalDescription);

        transaction.setDescription(merchant);
        transaction.setOriginalDescription(originalDescription);
        transaction.setAmount(amount);
        transaction.setTransactionType("debit");
        transaction.setMerchant(vendorRepository.findOrCreate(merchant));
        transaction.setCategory(categoryRepository.findByName(category));
        transaction.setAccount(accountRepository.findByAlias(cardNumber));
        transaction.setNotes("Fidelity");

        return transaction;
    }

}
