package com.nhn.minidooray.gateway.service.impl.temp;

import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.domain.enums.AccountStateType;
import com.nhn.minidooray.gateway.service.AccountApiService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TempAccountApiService implements AccountApiService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Account getAccountById(String id) {
        return Account.builder()
                .id("testId")
                .name("testName")
                .email("testEmail")
                .salt("salt")
                .password(passwordEncoder.encode("1"))
                .accountStateCode("04")
                .lastLoginAt(LocalDateTime.now())
                .accountStateChangeAt(LocalDateTime.now())
                .createAt(LocalDateTime.now())
                .build();
    }

    @Override
    public Account getAccountByEmail(String email) {
        return Account.builder()
                .id("testId")
                .name("testName")
                .email("testEmail")
                .salt("")
                .password(passwordEncoder.encode("1"))
                .accountStateCode("04")
                .lastLoginAt(LocalDateTime.now())
                .accountStateChangeAt(LocalDateTime.now())
                .createAt(LocalDateTime.now())
                .build();
    }

    @Override
    public Account addAccount(Account account) {
        return Account.builder()
                .id("testId")
                .name("testName")
                .email("testEmail")
                .salt("")
                .password(passwordEncoder.encode("1"))
                .accountStateCode("04")
                .lastLoginAt(LocalDateTime.now())
                .accountStateChangeAt(LocalDateTime.now())
                .createAt(LocalDateTime.now())
                .build();
    }

    @Override
    public void addAccountState(String id, AccountStateType accountStateType) {

    }
}
