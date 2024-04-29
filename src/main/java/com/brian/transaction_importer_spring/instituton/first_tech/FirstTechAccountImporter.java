package com.brian.transaction_importer_spring.instituton.first_tech;

import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FirstTechAccountImporter {
    @Autowired
    private AccountRepository accountRepository;

    // In the main body of the email, figure out which account we are extracting the balance of.
    public MinimumAccount[] parseAccountInfo(Document document) {
        String title = document.body().select("h1#title").text().strip();
        log.info("Title: {}", title);
        Elements accounts = document.body().select("strong");
        MinimumAccount[] parsedAccounts = new MinimumAccount[accounts.size()];
        for(int i = 0; i < accounts.size(); i++) {
            Element account = accounts.get(i);
            parsedAccounts[i] = parseAccount(account);
        }

        return parsedAccounts;
    }


    // For each account in the email, extract the account details
    MinimumAccount parseAccount(Element account) {
        Element accountParent = account.parent();
        String accountName = account.text().strip().split(" - ")[0];
        String accountNumber = account.text().strip().split(" - ")[1].replace("*", "").replace("#", "");
        String balance = accountParent.text().split("Current Balance: ")[1].split("Available Balance")[0].replace("$", "").replace(",", "");

        log.info("Account Name: {}", accountName);
        log.info("Account Number: {}", accountNumber);
        log.info("Balance: {}", balance);

        return new MinimumAccount(accountName, accountNumber, Double.parseDouble(balance));
    }

    // Extract all account details from the balance summary email
    public void handleBalanceSummary(String text) {
        Document soup = Jsoup.parse(text);
        MinimumAccount[] minimumAccounts = parseAccountInfo(soup);
        for(MinimumAccount minimumAccount : minimumAccounts) {
            Account account = accountRepository.findByAlias(minimumAccount.getNumber());
            account.setBalance(minimumAccount.getBalance());
            accountRepository.save(account);
        }
    }

    @Getter
    @AllArgsConstructor
    private class MinimumAccount {
        private String name;
        private String number;
        private Double balance;
    }
}
