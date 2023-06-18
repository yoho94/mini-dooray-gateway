package com.nhn.minidooray.gateway.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectModifyRequest {
    private Long projectId;
    private String projectName;
    private String projectStateCode;
}
