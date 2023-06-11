package com.nhn.minidooray.gateway.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectCreateRequest {
    private String accountId;
    @NotNull
    @NotBlank
    private String projectName;
}
