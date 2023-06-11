package com.nhn.minidooray.gateway.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectModifyStateRequest {
    @NotNull
    private Long projectId;
    @NotNull
    private String stateCode;
}
