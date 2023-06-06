package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.mapping.project")
@Getter
@Setter
public class ProjectMappingProperties {
    private String prefix;
    private String create;
    private String modify;
    private String delete;
}
