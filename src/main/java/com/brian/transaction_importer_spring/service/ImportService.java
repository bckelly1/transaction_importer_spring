package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.config.MailConfig;
import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    public List<Transaction> beginTransactionImport() {
        MailMessage[] transactionUnreadMessages = gmailService.getUnreadMessages("Transaction", mailConfig.getTransactionLabel());
        MailMessage[] chargeWasAuthorizedUnreadMessages = gmailService.getUnreadMessages("A charge was authorized", mailConfig.getTransactionLabel());
        MailMessage[] cardNotPresentUnreadMessages = gmailService.getUnreadMessages("card was not present", mailConfig.getTransactionLabel());

        List<Transaction> transactions = handleTransactions(transactionUnreadMessages);
        transactions.addAll(handleTransactions(chargeWasAuthorizedUnreadMessages));
        transactions.addAll(handleTransactions(cardNotPresentUnreadMessages));

        return transactions;
    }

    private @NotNull List<Transaction> handleTransactions(final MailMessage[] allMessages) {
        List<Transaction> transactionList = new ArrayList<>();
        for (MailMessage unreadMessage : allMessages) {
            Transaction[] transactions = transactionParserService.parseTransaction(unreadMessage);
            gmailService.markAsRead(unreadMessage);
            transactionList.addAll(List.of(transactions));
        }
        return transactionList;
    }

    public void beginBalanceSummaryImport() {
        MailMessage[] firstTechMessages = gmailService.getUnreadMessages("Balance Summary Alert", mailConfig.getBalanceLabel());
        balanceImporterService.parseBalanceSummary(firstTechMessages);
        markRead(firstTechMessages);

        MailMessage[] fidelityMessages = gmailService.getUnreadMessages("Daily Balance", mailConfig.getBalanceLabel());
        balanceImporterService.parseBalanceSummary(fidelityMessages);
        markRead(fidelityMessages);
    }

    private void markRead(final MailMessage[] mailMessages) {
        for (MailMessage mailMessage : mailMessages) {
            gmailService.markAsRead(mailMessage);
        }
    }
}
