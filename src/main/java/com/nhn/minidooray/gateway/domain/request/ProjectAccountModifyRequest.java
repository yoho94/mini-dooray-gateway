package com.nhn.minidooray.gateway.domain.request;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Generated
public class ProjectAccountModifyRequest {
    @NotNull
    @NotBlank
    private String authorityCode;
}
