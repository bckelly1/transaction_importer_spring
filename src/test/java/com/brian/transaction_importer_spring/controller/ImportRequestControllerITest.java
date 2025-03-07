package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.TestBaseUtils;
import com.brian.transaction_importer_spring.config.MailConfig;
import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.service.GmailService;
import jakarta.transaction.Transactional;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ImportRequestControllerITest extends TestBaseUtils {
    @Mock
    GmailService gmailService;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MailConfig mailConfig;

    @Test
    void importTransactionsRequestTest() {
        String fidelityTransactionEmail = loadFileContents(String.join(File.separator, "examples", "fidelity_credit_card_transaction.html"));
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setHtml(fidelityTransactionEmail);
        mailMessage.setFrom("Fidelity@fidelity.com");

        MailMessage[] mailMessages = new MailMessage[]{mailMessage};
        Mockito.when(gmailService.getUnreadMessages(Mockito.anyString(), Mockito.anyString())).thenReturn(mailMessages);
        String uri = "http://localhost:" + port + "/import-transactions";

        String forObject = this.restTemplate.getForObject(uri, String.class);
        System.out.println();
    }

    @Test
    void importFirstTechPaymentTransactionRequestTest() {
        String fidelityTransactionEmail = loadFileContents(String.join(File.separator, "examples", "first_tech_payment_transactions.html"));
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setHtml(fidelityTransactionEmail);
        mailMessage.setFrom("firsttech@firsttech.com");

        MailMessage[] mailMessages = new MailMessage[]{mailMessage};
        Mockito.when(gmailService.getUnreadMessages(Mockito.anyString(), Mockito.anyString())).thenReturn(mailMessages);
        String uri = "http://localhost:" + port + "/import-transactions";

        String forObject = this.restTemplate.getForObject(uri, String.class);
        System.out.println();
    }

    @Test
    void importFirstTechDepositTransactionRequestTest() {
        String fidelityTransactionEmail = loadFileContents(String.join(File.separator, "examples", "first_tech_deposit_transaction.html"));
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setHtml(fidelityTransactionEmail);
        mailMessage.setFrom("firsttech@firsttech.com");

        MailMessage[] mailMessages = new MailMessage[]{mailMessage};
        Mockito.when(gmailService.getUnreadMessages(Mockito.anyString(), Mockito.anyString())).thenReturn(mailMessages);
        String uri = "http://localhost:" + port + "/import-transactions";

        String forObject = this.restTemplate.getForObject(uri, String.class);
        System.out.println();
    }

    @Test
    void importFirstTechTransferTransactionRequestTest() {
        String fidelityTransactionEmail = loadFileContents(String.join(File.separator, "examples", "first_tech_cross_account_transfer_transaction.html"));
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setHtml(fidelityTransactionEmail);
        mailMessage.setFrom("firsttech@firsttech.com");

        MailMessage[] mailMessages = new MailMessage[]{mailMessage};
        Mockito.when(gmailService.getUnreadMessages(Mockito.anyString(), Mockito.anyString())).thenReturn(mailMessages);
        String uri = "http://localhost:" + port + "/import-transactions";

        String forObject = this.restTemplate.getForObject(uri, String.class);
        System.out.println();
    }

    //    @Test
    void importBalanceSummaryRequest() {
        String firstTechTransactionEmail = loadFileContents(String.join(File.separator, "examples", "first_tech_account_summary_example.html"));
        MailMessage firstTechMailMessage = createMockMailMessage();
        firstTechMailMessage.setHtml(firstTechTransactionEmail);
        firstTechMailMessage.setFrom("firsttech@firsttech.com");

        MailMessage[] mailMessages = new MailMessage[]{firstTechMailMessage};
        Mockito.when(gmailService.getUnreadMessages("Balance Summary Alert", mailConfig.getBalanceLabel())).thenReturn(mailMessages);

        String fidelityTransactionEmail = loadFileContents(String.join(File.separator, "examples", "fidelity_balance_summary_alert.html"));
        MailMessage fidelityMailMessage = createMockMailMessage();
        fidelityMailMessage.setHtml(fidelityTransactionEmail);
        fidelityMailMessage.setFrom("firsttech@firsttech.com");

        MailMessage[] fidelityMailMessages = new MailMessage[]{fidelityMailMessage};
        Mockito.when(gmailService.getUnreadMessages("Daily Balance", mailConfig.getBalanceLabel())).thenReturn(fidelityMailMessages);

        String uri = "http://localhost:" + port + "/import-balance-summary";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(uri)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.code());
            System.out.println(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


