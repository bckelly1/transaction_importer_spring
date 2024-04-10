package com.brian.transaction_importer_spring.institution;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.instituton.FidelityParser;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class FidelityParserTest {
    @Value("classpath:examples/fidelity_credit_card_transaction.txt")
    Resource transactionEmailText;

    @MockBean
    VendorRepository vendorRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @Autowired
    FidelityParser fidelityParser;

    private String loadFileContents(String fileName) {
        List<String> contents;
        try {
            contents = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.join("\n", contents);
    }

    private MailMessage createMockMailMessage() {
        String to = "to@example.com";
        String from = "from@example.com";
        String subject = "subject";
        String body = "";
        String html = "";
        String messageId = "messageId";
        Map<String, String> headers = new HashMap<>();
        return new MailMessage(to, from, subject, body, html, messageId, headers);
    }

    @Test
    void fidelityTransactionParseTest() throws IOException {
        Mockito.when(categoryRepository.findByName(Mockito.any())).thenReturn(null);
        Mockito.when(accountRepository.findByAlias(Mockito.any())).thenReturn(null);
        Mockito.when(vendorRepository.findOrCreate(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(null);

        String contents = loadFileContents(transactionEmailText.getURI().getPath());
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setBody(contents);
        mailMessage.setHtml(null);

        fidelityParser.handleTransaction(mailMessage);
    }
}