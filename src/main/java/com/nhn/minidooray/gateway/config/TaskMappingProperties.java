package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "com.nhn.minidooray.mapping.task")
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class TaskMappingProperties {
    private final ProjectMappingProperties projectMappingProperties;

    private String prefix;

    private String list;
    private String read;
    private String write;
    private String modify;
    private String delete;

    public String getWriteUrl(Long projectId) {
        return projectMappingProperties.getPrefix() + "/" + projectId
                + prefix
                + write;
    }

    public String getModifyUrl(Long projectId, Long taskId) {
        return projectMappingProperties.getPrefix() + "/" + projectId
                + prefix + "/" + taskId
                + modify;
    }

    public String getDeleteUrl(Long projectId, Long taskId) {
        return projectMappingProperties.getPrefix() + "/" + projectId
                + prefix + "/" + taskId
                + delete;
    }
}