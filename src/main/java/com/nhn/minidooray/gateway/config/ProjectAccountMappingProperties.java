package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "com.nhn.minidooray.mapping.project-account")
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class ProjectAccountMappingProperties {
    private String prefix;

    private String list;
    private String read;
    private String write;
    private String modify;
    private String delete;

    private final ProjectMappingProperties projectMappingProperties;

    public String getListUrl(Long projectId) {
        return projectMappingProperties.getPrefix() + "/" + projectId + prefix + list;
    }

    public String getReadUrl(Long projectId) {
        return projectMappingProperties.getPrefix() + "/" + projectId + prefix + read;
    }

    public String getWriteUrl(Long projectId) {
        return projectMappingProperties.getPrefix() + "/" + projectId + prefix + write;
    }

    public String getModifyUrl(Long projectId) {
        return projectMappingProperties.getPrefix() + "/" + projectId + prefix + modify;
    }

    public String getDeleteUrl(Long projectId) {
        return projectMappingProperties.getPrefix() + "/" + projectId + prefix + delete;
    }
}
