package com.nhn.minidooray.gateway.service;

import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.domain.enums.AccountStateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountApiService {
    Account getAccountById(String id);

    Account getAccountByEmail(String email);

    void addAccount(Account account);

    void updateAccount(Account account);

    void addAccountState(String id, AccountStateType accountStateType);

    void updateAccountLastLoginAt(Authentication authentication);

    List<Account> getAccountsById(String[] ids);

    Page<Account> getAccounts(Pageable pageable);
}
