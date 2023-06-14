package com.nhn.minidooray.gateway.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
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
    @JsonProperty("milestoneId")
    private Long mileStoneId;
    private List<String> tagNameList;

    public List<String> getTagNameList() {
        return tagNameList == null ? Collections.emptyList() : tagNameList;
    }
}
