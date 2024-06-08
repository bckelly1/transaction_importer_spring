package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.config.MailConfig;
import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final GmailService gmailService;

    private final MailConfig mailConfig;

    private final TransactionParserService transactionParserService;

    private final BalanceImporterService balanceImporterService;

    public List<Transaction> beginTransactionImport(){
        MailMessage[] unreadMessages = gmailService.getUnreadMessages("Transaction", mailConfig.getTransactionLabel());

        List<Transaction> transactionList = new ArrayList<>();
        for (MailMessage unreadMessage : unreadMessages) {
            Transaction[] transactions = transactionParserService.parseTransaction(unreadMessage);
            gmailService.markAsRead(unreadMessage);
            transactionList.addAll(List.of(transactions));
        }
        return transactionList;
    }

    public void beginBalanceSummaryImport(){
        MailMessage[] firstTechMessages = gmailService.getUnreadMessages("Balance Summary Alert", mailConfig.getBalanceLabel());
        balanceImporterService.parseBalanceSummary(firstTechMessages);
        markRead(firstTechMessages);

        MailMessage[] fidelityMessages = gmailService.getUnreadMessages("Daily Balance", mailConfig.getBalanceLabel());
        balanceImporterService.parseBalanceSummary(fidelityMessages);
        markRead(fidelityMessages);
    }

    private void markRead(MailMessage[] mailMessages) {
        for(MailMessage mailMessage : mailMessages) {
            gmailService.markAsRead(mailMessage);
        }
    }
}
