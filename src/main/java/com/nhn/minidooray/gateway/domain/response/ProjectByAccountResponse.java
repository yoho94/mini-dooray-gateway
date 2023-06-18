package com.nhn.minidooray.gateway.domain.response;

import lombok.Getter;

@Getter
public class ProjectByAccountResponse {
    private String accountId;
    private Long projectId;
    private String projectName;
    private String projectStateCode;
    private String accountAuthority;
}
