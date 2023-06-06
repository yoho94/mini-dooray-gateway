package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.mapping.api.account")
@Getter
@Setter
public class AccountApiMappingProperties {
    private String prefix;

    private String createAccount;
    private String readAccountById;
    private String readAccountByEmail;
    private String readAccountList;
    private String updateAccount;
    private String deleteAccount;
    private String updateAccountLastLoginAt;

    private String createAccountState;
}
