package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "com.nhn.minidooray.mapping.mile-stone")
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class MileStoneMappingProperties {
    private final ProjectMappingProperties projectMappingProperties;

    private String prefix;

    private String list;
    private String read;
    private String write;
    private String modify;
    private String delete;

    public String getListUrl(Long projectId) {
        return projectMappingProperties.getPrefix() + "/" + projectId
                + prefix
                + list;
    }
}
