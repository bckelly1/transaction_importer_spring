package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.TestBaseUtils;
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
public class ImportRequestControllerTest extends TestBaseUtils {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    GmailService gmailService;

    @Test
    void importTransactionsRequestTest() {
        String fidelityTransactionEmail = loadFileContents(String.join(File.separator, "examples", "fidelity_credit_card_transaction.html"));
        MailMessage mailMessage = createMockMailMessage();
        mailMessage.setHtml(fidelityTransactionEmail);
        mailMessage.setFrom("Fidelity@fidelity.com");

        MailMessage[] mailMessages = new MailMessage[]{mailMessage};
        Mockito.when(gmailService.getUnreadMessages(Mockito.anyString())).thenReturn(mailMessages);
        String uri = "http://localhost:" + port + "/import-transactions";

        String forObject = this.restTemplate.getForObject(uri, String.class);
        System.out.println();

    }

    @Test
    void importBalanceSummaryRequest() {
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


