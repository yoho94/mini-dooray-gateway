package com.nhn.minidooray.gateway.service;

import com.nhn.minidooray.gateway.domain.enums.AccountStateType;
import com.nhn.minidooray.gateway.domain.Account;

public interface AccountApiService {
    Account getAccountById(String id);

    Account getAccountByEmail(String email);

    Account addAccount(Account account);

    void addAccountState(String id, AccountStateType accountStateType);
}
