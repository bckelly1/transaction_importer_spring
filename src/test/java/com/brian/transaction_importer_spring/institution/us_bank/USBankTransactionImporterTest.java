package com.brian.transaction_importer_spring.institution.us_bank;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.institution.usbank.USBankTransactionImporter;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import org.jsoup.Jsoup;
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
class USBankTransactionImporterTest {
    private static final String transactionEmailText = String.join(File.separator, "examples", "us_bank_transaction.html");

    @MockBean
    VendorRepository vendorRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @Autowired
    USBankTransactionImporter usBankTransactionImporter;

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

    private String convertHtmlToText(String html) {
        return Jsoup.parse(html).text();
    }

    private MailMessage createMockMailMessage() {
        String to = "to@example.com";
        String from = "from@example.com";
        String subject = "subject";
        String body = "";
        String html = "";
        String messageId = "messageId";
        String label = "";
        Map<String, String> headers = new HashMap<>();
        headers.put("Custom-Epoch", "1712617137");
        return new MailMessage(to, from, subject, body, html, messageId, label, headers);
    }

    @Test
    void usbankTransactionParseTest() {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);

        String contents = loadFileContents(transactionEmailText);
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setBody(convertHtmlToText(contents));
        mailMessage.setHtml(contents);

        usBankTransactionImporter.handleTransactionEmail(mailMessage);
    }
}