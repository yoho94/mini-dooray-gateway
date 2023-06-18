package com.nhn.minidooray.gateway.domain.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MileStoneByGetTaskResponse {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
