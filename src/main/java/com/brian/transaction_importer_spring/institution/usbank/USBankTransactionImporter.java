package com.brian.transaction_importer_spring.institution.usbank;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import com.brian.transaction_importer_spring.service.CategoryInfererService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
@Log4j2
@RequiredArgsConstructor
public class USBankTransactionImporter {

    private final CategoryInfererService categoryInfererService;

    private final CategoryRepository categoryRepository;

    private final VendorRepository vendorRepository;

    private final AccountRepository accountRepository;

    public Transaction handleTransactionEmail(MailMessage mailMessage) {
        return handleTransactions(mailMessage);
    }

    private Transaction handleTransactions(MailMessage mailMessage) {
        String body = mailMessage.getBody();
        String[] lines = body.split("\\. ");
        String originalDescription = String.join(" ", lines[1].split(" ")).replace("Log in ", "");

        String[] cardNumberSections = lines[2].split(" ");
        String cardNumber = cardNumberSections[cardNumberSections.length - 1];

        String[] tokens = originalDescription.split(" ");
        Double amount = Double.parseDouble(tokens[5].replace("$", ""));
        String merchant = "";
        for(int i = 7; i < tokens.length; i++) { // TODO: Could do an overrun
            String token = tokens[i];
            if(merchant.isEmpty()) {
                merchant = token;
            }
            else{
                merchant = String.join(" ", merchant, token);
            }
        }
        String category = categoryInfererService.getCategory(originalDescription);

        log.info("Card Number: {}", cardNumber);
        log.info("amount: {}", amount);
        log.info("detail: {}\n", originalDescription);

        Transaction transaction = new Transaction();
        transaction.setDate(new Timestamp(Long.parseLong(mailMessage.getHeaders().get("Custom-Epoch")) * 1000L));
        transaction.setMailMessageId(mailMessage.getMessageId());
        transaction.setDescription(originalDescription);
        transaction.setOriginalDescription(originalDescription);
        transaction.setAmount(amount);
        transaction.setTransactionType("debit");
        transaction.setCategory(categoryRepository.findByName(category));
        transaction.setMerchant(vendorRepository.findOrCreate(merchant));
        transaction.setAccount(accountRepository.findByAlias(cardNumber));
        transaction.setNotes("US Bank");

        return transaction;
    }
}
