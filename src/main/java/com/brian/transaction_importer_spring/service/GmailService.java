package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.config.MailConfig;
import com.brian.transaction_importer_spring.entity.MailMessage;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Header;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.FlagTerm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class GmailService {
    final MailConfig mailConfig;

    public MailMessage[] getUnreadMessages(String labelName) {
        // Gmail IMAP properties
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");

        try {
            // Create session and authenticate
            Session session = Session.getInstance(properties, null);
            Store store = session.getStore("imaps");
            store.connect(mailConfig.getHost(), mailConfig.getUsername(), mailConfig.getPassword());

            // Open inbox folder
            Folder inbox = store.getFolder(labelName);
            inbox.open(Folder.READ_ONLY);

            // Fetch unread messages
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            MailMessage[] mailMessages = parseMailMessages(messages);

            // Close resources
            inbox.close(false);
            store.close();

            return mailMessages;
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return new MailMessage[0];
        }
    }

    // When store.close() is called, the messages in the array are no longer accessible. We need to copy out (or process)
    //   the messages, so we can work on them independently.
    private MailMessage[] parseMailMessages(Message[] messages) {
        MailMessage[] mailMessages = new MailMessage[messages.length];
        for (int i = 0; i < messages.length; i++) {
            MailMessage mailMessage = parseMailMessage(messages[i]);
            mailMessages[i] = mailMessage;
        }
        return mailMessages;
    }

    private MailMessage parseMailMessage(Message message) {
        try {
            Map<String, String> headers = parseHeaders(message);
            String from = headers.get("From");
            String to = headers.get("To");
            String subject = headers.get("Subject");
            String body = parseBody(message);
            String html = body.contains("<html") ? body : null; // If we don't detect HTML in the body, assume it's not HTML
            String messageId = headers.get("Message-ID").replace("<", "").replace(">", "");
            MailMessage mailMessage = new MailMessage(from, to, subject, body, html, messageId, headers);
            return mailMessage;
        }
        catch (IOException | MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseBody(Message message) throws IOException, MessagingException {
        if(message.getContent().getClass() == MimeMultipart.class) {
            MimeMultipart content = ((MimeMultipart) message.getContent());
            int length = content.getCount();
            for(int i = 0; i < length; i++) {
                if(content.getBodyPart(i).isMimeType("text/plain")) {
                    return content.getBodyPart(i).getContent().toString();
                }
            }
        }
        return message.getContent().toString();
    }

    private Map<String, String> parseHeaders(Message message) throws MessagingException {
        Map<String, String> headers = new HashMap<>();
        Iterator<Header> iterator = message.getAllHeaders().asIterator();
        while(iterator.hasNext()) {
            Header header = iterator.next();
            if(header.getName().equals("ARC-Seal")) {
                String epoch = header.getValue().split("; ")[2].split("=")[1];
                headers.put("Custom-Epoch", epoch);
            }
            else{
                headers.put(header.getName(), header.getValue());
            }
        }
        return headers;
    }
}

