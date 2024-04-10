package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.enums.KnownInstitution;
import com.brian.transaction_importer_spring.instituton.FidelityParser;
import com.brian.transaction_importer_spring.instituton.FirstTechParser;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionParserService {
    @Autowired
    private FidelityParser fidelityParser;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FirstTechParser firstTechParser;

    public Transaction[] parseTransactions(MailMessage[] mailMessages) {
        Transaction[] transactions = new Transaction[mailMessages.length];
        for (int i = 0; i < mailMessages.length; i++) {
            transactions[i] = parseTransaction(mailMessages[i]);
        }
        return transactions;
    }

    private Transaction parseTransaction(MailMessage mailMessage) {
        KnownInstitution knownInstitution = parseInstitution(mailMessage);
        if(knownInstitution == KnownInstitution.FIDELITY) {
            fidelityParser.handleTransaction(mailMessage);
        }
        else if (knownInstitution == KnownInstitution.FIRST_TECH) {
            firstTechParser.handleTransactionEmail(mailMessage);
        }

        return null;
//        return transactionRepository.save(transaction);
    }

    public void parseBalanceSummary(MailMessage[] mailMessages) {
        for(MailMessage mailMessage : mailMessages) {
            KnownInstitution knownInstitution = parseInstitution(mailMessage);
            if(knownInstitution == KnownInstitution.FIDELITY) {
                log.error("Fidelity Balance importer not implemented!");
            }
            else if (knownInstitution == KnownInstitution.FIRST_TECH) {
                firstTechParser.handleBalanceSummary(mailMessage.getHtml());
            }
        }
    }

    private KnownInstitution parseInstitution(MailMessage mailMessage) {
        String fromField = mailMessage.getHeaders().get("From").toLowerCase();
        if(fromField.contains("fidelity")) {
            return KnownInstitution.FIDELITY;
        }
        else if(fromField.contains("first tech")) {
            return KnownInstitution.FIRST_TECH;
        }
        else if(fromField.contains("us bank")) {
            return KnownInstitution.US_BANK;
        }
        else if(fromField.contains("home depot")) {
            return KnownInstitution.HOME_DEPOT;
        }
        throw new IllegalStateException("Unknown institution: " + fromField);
    }
}