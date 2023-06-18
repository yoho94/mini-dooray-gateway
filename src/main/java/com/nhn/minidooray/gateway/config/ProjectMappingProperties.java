package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "com.nhn.minidooray.mapping.project")
@Getter
@Setter
@Component
public class ProjectMappingProperties {
    private String prefix;
    private String list;
    private String create;
    private String modify;
    private String modifyState;
    private String delete;

    public String getCreateUrl() {
        return prefix + create;
    }
}
