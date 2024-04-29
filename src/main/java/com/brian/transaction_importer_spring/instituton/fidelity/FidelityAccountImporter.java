package com.brian.transaction_importer_spring.instituton.fidelity;

import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Log4j2
public class FidelityAccountImporter {
    @Autowired
    private AccountRepository accountRepository;

    public void handleBalanceSummary(String summaryEmailText) {
        Document document = org.jsoup.Jsoup.parse(summaryEmailText);
        Elements elements = document.select("td:contains(XXXXX)");
        Element element = elements.getLast();
        String text = element.text();

        String[] tokens = text.split(" ");
        String accountNumber = tokens[1].replaceAll("X", "");
        double balance = Double.parseDouble(tokens[6].replace("$", "").replace(",", ""));
        String date = tokens[tokens.length - 1].replace(".", "");

        Account account = accountRepository.findByAlias(accountNumber);
        account.setBalance(balance);
        account.setLast_updated(Timestamp.valueOf(date));
        accountRepository.save(account);
    }
}
