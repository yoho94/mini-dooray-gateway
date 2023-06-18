package com.nhn.minidooray.gateway.domain.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TasksResponse {
    private Long projectId;
    private Long id;
    private String title;
    private String content;
    private String writerId;
    private LocalDateTime createAt;
}
