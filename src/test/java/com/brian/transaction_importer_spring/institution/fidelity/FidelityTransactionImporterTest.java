package com.brian.transaction_importer_spring.institution.fidelity;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
class FidelityTransactionImporterTest {
    private static final String transactionEmailText = String.join(File.separator, "examples", "fidelity_credit_card_transaction.html");

    private static final String summaryEmailText = String.join(File.separator, "examples", "fidelity_balance_summary_alert.html");

    @MockBean
    VendorRepository vendorRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @Autowired
    FidelityTransactionImporter fidelityParser;

    private String loadFileContents(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream(fileName)) {
            if (is != null) {
                return new String(is.readAllBytes());
            } else {
                throw new RuntimeException("Problem reading file: " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MailMessage createMockMailMessage() {
        String to = "to@example.com";
        String from = "from@example.com";
        String subject = "Transaction";
        String body = "";
        String html = "";
        String messageId = "messageId";
        String label = "";
        Map<String, String> headers = new HashMap<>();
        headers.put("Custom-Epoch", "1712617137");
        return new MailMessage(to, from, subject, body, html, messageId, label, headers);
    }

    @Test
    void fidelityTransactionParseTest() {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);

        String contents = loadFileContents(transactionEmailText);
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setBody(contents);
        mailMessage.setHtml(null);

        fidelityParser.handleTransaction(mailMessage);
    }

    @Test
    void fidelitySummaryParseTest() {

        String contents = loadFileContents(summaryEmailText);
        Document document = Jsoup.parse(contents);
        Elements elements = document.select("td:contains(XXXXX)");
        Element element = elements.getLast();
        String text = element.text();

        String[] tokens = text.split(" ");
        String account = tokens[1].replaceAll("X", "");
        double balance = Double.parseDouble(tokens[6].replace("$", "").replace(",", ""));
        String date = tokens[tokens.length - 1].replace(".", "");

        System.out.println();
    }
}