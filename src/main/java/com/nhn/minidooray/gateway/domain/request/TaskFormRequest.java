package com.nhn.minidooray.gateway.domain.request;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Generated
public class TaskFormRequest {
    private Long id;
    private String title;
    private String content;
    private Long projectId;
    private Long taskId;
    private String writerId;
    private Long milestoneId;
    private List<TagByTaskFormRequest> tagList;
}
