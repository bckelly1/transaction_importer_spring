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

    public Transaction[] parseTransactions(MailMessage mailMessages) {
        return parseTransaction(mailMessages);
    }

    private Transaction[] parseTransaction(MailMessage mailMessage) {
        KnownInstitution knownInstitution = parseInstitution(mailMessage);
        Transaction[] transactions = null;
        if(knownInstitution == KnownInstitution.FIDELITY) {
            Transaction transaction = fidelityParser.handleTransaction(mailMessage);
            transactions = new Transaction[]{transaction};
        }
        else if (knownInstitution == KnownInstitution.FIRST_TECH) {
            transactions = firstTechParser.handleTransactionEmail(mailMessage);
        }

        assert transactions != null;
        for(Transaction transaction : transactions) {
            transactionRepository.save(transaction);
        }
        return transactions;
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
