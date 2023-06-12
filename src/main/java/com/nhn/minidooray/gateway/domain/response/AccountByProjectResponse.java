package com.nhn.minidooray.gateway.domain.response;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Generated
public class AccountByProjectResponse {
    private Long projectId;
    private String projectName;
    private String accountId;
    private String accountName;
    private String authorityCode;
    private String authority;
}