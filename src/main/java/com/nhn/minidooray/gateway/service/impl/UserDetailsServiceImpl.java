package com.nhn.minidooray.gateway.service.impl;

import com.nhn.minidooray.gateway.domain.enums.AccountStateType;
import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.service.AccountApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountApiService accountApiService;

    @Value("${com.nhn.minidooray.gateway.dormant_days}")
    private long dormantDays;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = null;
        try {
            account = accountApiService.getAccountById(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(username + "Not Found");
        }

        AccountStateType accountStateType = AccountStateType.valueOfCode(account.getAccountStateCode());
        LocalDateTime lastLoginAt = account.getLastLoginAt();
        LocalDateTime accountStateChangeAt = account.getAccountStateChangeAt();

        long loginDays = Duration.between(lastLoginAt, LocalDateTime.now()).toDays();
        long changeDays = Duration.between(accountStateChangeAt, LocalDateTime.now()).toDays();

        boolean accountLocked = accountStateType.isAccountLocked();

        // 계정 잠금(휴면)이 아니라면 아래 휴면 계정 검사 로직 start
        if (!accountLocked && (dormantDays <= loginDays)) {
            if (accountStateType == AccountStateType.ACTIVE) {
                if (changeDays >= dormantDays) {
                    accountApiService.addAccountState(username, AccountStateType.DORMANT);
                    accountLocked = true;
                }
            } else {
                accountApiService.addAccountState(username, AccountStateType.DORMANT);
                accountLocked = true;
            }
        }

        return User.builder()
                .username(account.getId())
                .password(account.getPassword())
                .disabled(accountStateType.isDisabled())
                .accountLocked(accountLocked)
                .authorities(Collections.emptySet())
                .build();
    }
}
