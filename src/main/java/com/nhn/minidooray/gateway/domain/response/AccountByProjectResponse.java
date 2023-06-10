package com.nhn.minidooray.gateway.domain.response;

import lombok.Getter;

@Getter
public class AccountByProjectResponse {
    private Long projectId;
    private String projectName;
    private String accountId;
    private String authorityCode;
    private String authority;
}