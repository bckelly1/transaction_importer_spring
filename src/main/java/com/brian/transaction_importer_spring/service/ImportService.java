package com.brian.transaction_importer_spring.service;

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
    @Autowired
    private GmailService gmailService;

    @Autowired
    private TransactionParserService transactionParserService;

    @Autowired
    private BalanceImporterService balanceImporterService;

    public List<Transaction> beginTransactionImport(){
        MailMessage[] unreadMessages = gmailService.getUnreadMessages("Transaction");

        List<Transaction> transactionList = new ArrayList<>();
        for(int i = 0; i < unreadMessages.length; i++){
            MailMessage unreadMessage = unreadMessages[i];
            Transaction[] transactions = transactionParserService.parseTransactions(unreadMessage);
            gmailService.markAsRead(unreadMessage);
            transactionList.addAll(List.of(transactions));
        }
        return transactionList;
    }

    public void beginBalanceSummaryImport(){
        MailMessage[] firstTechMessages = gmailService.getUnreadMessages("Balance Summary Alert");
        balanceImporterService.parseBalanceSummary(firstTechMessages);

        MailMessage[] fidelityMessages = gmailService.getUnreadMessages("Daily Balance");
        balanceImporterService.parseBalanceSummary(fidelityMessages);
    }
}
