package com.nhn.minidooray.gateway.service;

import com.nhn.minidooray.gateway.domain.enums.AccountStateType;
import com.nhn.minidooray.gateway.domain.Account;
import org.springframework.security.core.Authentication;

public interface AccountApiService {
    Account getAccountById(String id);

    Account getAccountByEmail(String email);

    void addAccount(Account account);

    void updateAccount(Account account);

    void addAccountState(String id, AccountStateType accountStateType);

    void updateAccountLastLoginAt(Authentication authentication);
}
