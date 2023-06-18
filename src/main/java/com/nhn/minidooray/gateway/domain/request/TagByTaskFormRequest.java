package com.nhn.minidooray.gateway.domain.request;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Generated
public class TagByTaskFormRequest {
    private Long taskId;
    private Long tagId;
}
