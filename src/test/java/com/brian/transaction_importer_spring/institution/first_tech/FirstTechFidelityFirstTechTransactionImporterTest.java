package com.brian.transaction_importer_spring.institution.first_tech;

import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.instituton.first_tech.FirstTechAccountImporter;
import com.brian.transaction_importer_spring.instituton.first_tech.FirstTechTransactionImporter;
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
public class FirstTechFidelityFirstTechTransactionImporterTest {
    String transactionDepositEmail = String.join(File.separator, "examples", "first_tech_deposit_transaction.html");

    String transactionPaymentEmail = String.join(File.separator, "examples", "first_tech_payment_transactions.html");

    String transactionSummaryEmail = String.join(File.separator, "examples", "first_tech_account_summary_example.html");

    String transferEmail = String.join(File.separator, "examples", "first_tech_cross_account_transfer_transaction.html");

    @MockBean
    VendorRepository vendorRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @Autowired
    FirstTechTransactionImporter firstTechTransactionImporter;

    @Autowired
    FirstTechAccountImporter firstTechAccountImporter;

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
        headers.put("Custom-Epoch", "1712617137");
        return new MailMessage(to, from, subject, body, html, messageId, label, headers);
    }

    @Test
    void firstTechTransactionParserPaymentTest() {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);

        String htmlContent = loadFileContents(transactionPaymentEmail);
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setBody(htmlContent);
        mailMessage.setHtml(htmlContent);

        Transaction[] transactions = firstTechTransactionImporter.handleTransactionEmail(mailMessage);
        System.out.println();
    }

    @Test
    void firstTechTransactionParserDepositTest() {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);

        String htmlContent = loadFileContents(transactionDepositEmail);
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setBody(htmlContent);
        mailMessage.setHtml(htmlContent);

        Transaction[] transactions = firstTechTransactionImporter.handleTransactionEmail(mailMessage);
        System.out.println();
    }

    @Test
    void firstTechBalanceSummaryImportParserTest() {
        Account account = new Account();
        account.setName("Test");
        account.setAlias("Test");
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(account);
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);

        String htmlContent = loadFileContents(transactionSummaryEmail);
        firstTechAccountImporter.handleBalanceSummary(htmlContent);
    }

    @Test
    void firstTechTransferParserTest() {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);

        String htmlContent = loadFileContents(transferEmail);
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setBody(htmlContent);
        mailMessage.setHtml(htmlContent);

        firstTechTransactionImporter.handleTransactionEmail(mailMessage);
    }
}
