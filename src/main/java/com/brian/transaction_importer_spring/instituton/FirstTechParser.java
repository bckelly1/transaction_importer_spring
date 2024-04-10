package com.brian.transaction_importer_spring.instituton;

import com.brian.transaction_importer_spring.entity.MailMessage;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import com.brian.transaction_importer_spring.service.CategoryInfererService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;

@Service
@Log4j2
public class FirstTechParser {
    @Autowired
    private CategoryInfererService categoryInfererService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private AccountRepository accountRepository;

    // With Bank accounts, you have to determine if the money is going in (credit) or going out (debit)
    //    For First Tech, you can either determine that by looking for the parentheses () or the Credit/Debit label
    public String determineTransactionStyle(Document document) {
        String token = document.select("div[.transactions-table-header]").text().strip().split(" ")[0];
        if (token.equals("Deposits"))
           return "Credit";
        else if (token.equals("Withdrawals"))
            return "Debit";
        else
            //TODO: Problem !
            log.error("Could not determine transaction style!");
        return "Unknown";
    }


    // Is the money just moving between two user-owned accounts? While that does count as a Credit/Debit, in the grand scheme
    //   Of things, it doesn't affect the budget.  Mark the transaction as a transfer and it will be ignored.
    public boolean isTransfer(String title) {
        if (title.contains("Regular Payment Transfer"))
           return false;
        else if (title.contains("Transfer"))
            return true;
        else if (isCreditCardPayment(title))
            return true;
        return false;
    }


    // Credit card payments do and don't matter. They do matter because the money is literally leaving your bank account. On
    //   The other hand, you already spent the money, so the money is "gone" already, so we can think of the credit card
    //   Payment more as a transfer than as "money going out".  Let the rage debate commence.
    public boolean isCreditCardPayment(String title) {
        // Fidelity is a bit obnoxious about this. No email on payments. Have to infer it when the money goes out.
        if (title.contains("CARDMEMBER SERV - WEB PYMT"))
           return true;
        return false;
    }

    // Example input: Deposit Transfer From ******1234
    // Output: 1234
    public String transfer_source_account(String title) {
        String[] tokens = title.split(" ");
        for(String token : tokens) {
            if (token.contains("*"))
                return token.replace("*", "");
        }
        return "Unknown Source Account";
    }


    // Main handling of the transaction email. Read the transaction and extract transaction details from the text.
    public Transaction[] handleTransactionEmail(MailMessage mailMessage) {
        return parseHtml(mailMessage);
    }

    public Transaction handleTransactionRow(Element element, MailMessage mailMessage) {
        Timestamp transactionDate = new Timestamp(Long.parseLong(mailMessage.getHeaders().get("Custom-Epoch")) * 1000L);
        String transactionDetailsOriginal = element.select("td.details").text().strip();

        String[] transactionTokens = transactionDetailsOriginal.split(" ");
        String merchant = String.join(" ", Arrays.copyOfRange(transactionTokens, 2, transactionTokens.length));

        boolean transfer = isTransfer(transactionDetailsOriginal);
        String sourceAccount = transfer ? transfer_source_account(transactionDetailsOriginal) : null;

        String transactionAmount = element.select("td.trans-amount").text().strip().replace("$", "").replace(",", "");
        double amount;
        String transactionType;
        if (transactionAmount.contains("(")) {
            amount = Double.parseDouble(transactionAmount.replace("(", "").replace(")", ""));
            transactionType = "Debit";
        }
        else {
            amount = Double.parseDouble(transactionAmount);
            transactionType = "Credit";
        }

        String category = null;
        if (transactionDetailsOriginal.contains("Transfer")) {
            // Most likely this is a cross - account transfer, vendor / merchant is bank
            merchant = "First Tech";
            category = "Transfer";
        } else if (transactionDetailsOriginal.contains("Dividend")) {
            // Credit Dividend transaction, vendor / merchant is bank
            merchant = "First Tech";
            category = "Dividend";
        }
        else {
            category = categoryInfererService.getCategory(transactionDetailsOriginal);
//            category = Category.UNKNOWN; // TODO: Not necessary?
        }
        log.info("Info:");
        log.info("\tDate: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(transactionDate));
        log.info("\tDetail: " + merchant);
        log.info("\tAmount: " + amount);

        Transaction transaction = new Transaction();
        transaction.setDate(transactionDate);
        transaction.setMailMessageId(mailMessage.getHeaders().get("Message-ID"));
        transaction.setDescription(merchant);
        transaction.setOriginalDescription(transactionDetailsOriginal);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setCategory(categoryRepository.findByName(category));
        if (sourceAccount != null) {
            transaction.setMerchant(vendorRepository.findOrCreate(sourceAccount)); // source_account if transfer else merchant
        }
        else {
            transaction.setMerchant(vendorRepository.findOrCreate(merchant)); // source_account if transfer else merchant
        }
        transaction.setAccount(accountRepository.findByAlias(sourceAccount));
        transaction.setNotes("First Tech");

        return transaction;
    }


    // Parse the email's HTML text and extract relevant transaction info from it.
    public Transaction[] parseHtml(MailMessage mailMessage) {
        Document soup = Jsoup.parse(mailMessage.getHtml());
        String title = soup.body().select("h1#title").text().strip();
        String accountName = soup.body().select("strong").get(0).text().strip().split(" - ")[0];
        String accountNumber = soup.body().select("strong").get(0).text().strip().split(" - ")[1].replace("*", "");
        String balance = soup.body().select("p:contains(Balance)").get(0).text().split("Balance: ")[1].strip();

        log.info("Title: {}", title);
        log.info("Account Name: {}", accountName);
        log.info("Account Number: {}", accountNumber);
        log.info("Balance: {}", balance);

        String transaction_style = determineTransactionStyle(soup);
        Elements transactionElements = soup.body().select("tr.transaction-row");
        Transaction[] transactions = new Transaction[transactionElements.size()];
        for(int i = 0; i < transactionElements.size(); i++) {
            Element transactionElement = transactionElements.get(i);
            Transaction transaction = handleTransactionRow(transactionElement, mailMessage);
            if (!Objects.equals(transaction_style, transaction.getTransactionType()))
                log.error("Transaction style does not match parsed transaction type from transaction!");
            if (transaction.getMerchant() == null) {
                transaction.setMerchant(vendorRepository.findOrCreate(accountName));
                transaction.setAccount(accountRepository.findByAlias(accountNumber));  // TODO: possibly redundant?
            }
            transactions[i] = transaction;
        }
        return transactions;
    }


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

        return new MinimumAccount(accountName, accountNumber, balance);
    }

    // Extract all account details from the balance summary email
    public MinimumAccount[] handleBalanceSummary(String text) {
        Document soup = Jsoup.parse(text);
        return parseAccountInfo(soup);
    }

    @Getter
    @AllArgsConstructor
    private class MinimumAccount {
        private String name;
        private String number;
        private String balance;
    }

}