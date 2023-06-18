package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.mapping.account")
@Getter
@Setter
public class AccountMappingProperties {
    private String prefix;
    private String list;
    private String read;
    private String write;
    private String modify;
    private String delete;
}
