package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.url")
@Getter
@Setter
public class ApiUrlProperties {
    private String accountProtocol;
    private String accountHost;
    private String accountPort;

    private String taskProtocol;
    private String taskHost;
    private String taskPort;

    public String getAccountUrl() {
        return accountProtocol + "://" + accountHost + ":" + accountPort;
    }

    public String getTaskUrl() {
        return taskProtocol + "://" + taskHost + ":" + taskPort;
    }
}
