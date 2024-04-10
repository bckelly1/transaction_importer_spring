package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.config.MailConfig;
import com.brian.transaction_importer_spring.entity.MailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportService {
    @Autowired
    private GmailService gmailService;

    @Autowired
    private TransactionParserService transactionParserService;

    public void beginTransactionImport(){
        MailMessage[] unreadMessages = gmailService.getUnreadMessages();
        transactionParserService.parseTransactions(unreadMessages);
    }

    public void beginBalanceSummaryImport(){
        MailMessage[] unreadMessages = gmailService.getUnreadMessages();
        transactionParserService.parseBalanceSummary(unreadMessages);
    }
}
