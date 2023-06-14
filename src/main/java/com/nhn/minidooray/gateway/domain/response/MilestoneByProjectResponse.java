package com.nhn.minidooray.gateway.domain.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MilestoneByProjectResponse {
    private Long projectId;
    private Long milestoneId;
    private String milestoneName;
    private LocalDate startDate;
    private LocalDate endDate;
}
