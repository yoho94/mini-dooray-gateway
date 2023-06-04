package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.comment.mapping")
@Getter
@Setter
public class CommentMappingProperties {
    private String prefix;

    private String read;
    private String write;
    private String modify;
    private String delete;
}