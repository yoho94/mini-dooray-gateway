package com.nhn.minidooray.gateway.domain.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TaskResponse {
    private Long id;
    private Long projectId;
    private MileStoneByGetTaskResponse mileStone;
    private String writerId;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private List<TagByGetTaskResponse> taskTagList;

    public String getTagNameList() {
        return taskTagList.stream()
                .map(TagByGetTaskResponse::getTagName)
                .collect(Collectors.joining(","));
    }
}
