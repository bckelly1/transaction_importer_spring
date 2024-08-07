package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.institution.fidelity.FidelityTransactionImporter;
import com.brian.transaction_importer_spring.institution.first_tech.FirstTechTransactionImporter;
import com.brian.transaction_importer_spring.institution.usbank.USBankTransactionImporter;
import com.brian.transaction_importer_spring.repository.AccountHistoryRepository;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TransactionParserServiceTest {
    private static final String firstTechTransactionSummaryEmail = String.join(File.separator, "examples", "first_tech_account_summary_example.html");

    @MockBean
    VendorRepository vendorRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    AccountHistoryRepository accountHistoryRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    FidelityTransactionImporter fidelityParser;

    @MockBean
    FirstTechTransactionImporter firstTechTransactionImporter;

    @MockBean
    USBankTransactionImporter usBankTransactionImporter;

    @Autowired
    TransactionParserService transactionParserService;

    @Autowired
    BalanceImporterService balanceImporterService;

    private String loadFileContents(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream(fileName)) {
            if (is != null) {
                return new String(is.readAllBytes());
            }
            else{
                throw new RuntimeException("Problem reading file: " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MailMessage createMockMailMessage() {
        String to = "to@example.com";
        String from = "from@example.com";
        String subject = "subject";
        String body = "";
        String html = "";
        String label = "";
        String messageId = "messageId";
        Map<String, String> headers = new HashMap<>();
        return new MailMessage(to, from, subject, body, html, messageId, label, headers);
    }

    @Test
    void parseInstitutionTest_parseFidelity() {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);
        Transaction transaction = new Transaction();
        Mockito.when(fidelityParser.handleTransaction(Mockito.any())).thenReturn(transaction);

        MailMessage mailMessage = createMockMailMessage();
        mailMessage.getHeaders().put("From", "Fidelity Alerts");
        transactionParserService.parseTransaction(mailMessage);
    }

    @Test
    void parseInstitutionTest_parseFirstTech() {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);
        Transaction transaction = new Transaction();
        Transaction[] transactions = new Transaction[1];
        transactions[0] = transaction;
        Mockito.when(firstTechTransactionImporter.handleTransactionEmail(Mockito.any())).thenReturn(transactions);

        MailMessage mailMessage = createMockMailMessage();
        mailMessage.getHeaders().put("From", "First Tech Alerts");
        transactionParserService.parseTransaction(mailMessage);
    }

    @Test
    void parseInstitutionTest_parseUSBank() {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);
        Transaction transaction = new Transaction();
        Mockito.when(usBankTransactionImporter.handleTransactionEmail(Mockito.any())).thenReturn(transaction);

        MailMessage mailMessage = createMockMailMessage();
        mailMessage.getHeaders().put("From", "U.S. Bank Alerts");
        transactionParserService.parseTransaction(mailMessage);
    }

    @Test
    void parseBalanceSummaryTest() {
        Account account = new Account();
        account.setName("Test");
        account.setAlias("Test");
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(account);
        Mockito.when(accountHistoryRepository.save(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
        String fileContents = loadFileContents(firstTechTransactionSummaryEmail);
        MailMessage mockMailMessage = createMockMailMessage();
        mockMailMessage.setHtml(fileContents);
        mockMailMessage.setBody(fileContents);
        mockMailMessage.getHeaders().put("From", "First Tech Alerts");
        MailMessage[] mailMessages = new MailMessage[1];
        mailMessages[0] = mockMailMessage;

        balanceImporterService.parseBalanceSummary(mailMessages);
    }
}
