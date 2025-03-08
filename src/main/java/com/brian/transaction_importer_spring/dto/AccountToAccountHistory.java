package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.entity.AccountHistory;

public class AccountToAccountHistory {
    private AccountToAccountHistory() { }

    public static AccountHistory parse(final Account account) {
        AccountHistory accountHistory = new AccountHistory();
        accountHistory.setAccount(account);
        accountHistory.setBalance(account.getBalance());
        accountHistory.setTimestamp(account.getLastUpdated());

        return accountHistory;
    }
}
