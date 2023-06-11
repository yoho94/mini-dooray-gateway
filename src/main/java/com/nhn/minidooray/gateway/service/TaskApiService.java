package com.nhn.minidooray.gateway.service;

import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.domain.enums.ProjectStateType;
import com.nhn.minidooray.gateway.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyStateRequest;
import com.nhn.minidooray.gateway.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.gateway.domain.response.ProjectByAccountResponse;
import com.nhn.minidooray.gateway.domain.response.TasksResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface TaskApiService {
    ProjectAuthorityType getProjectAuthorityType(Long projectId, String accountId);

    ProjectStateType getProjectStateType(Long projectId);

    void createProject(Authentication authentication, ProjectCreateRequest projectCreateRequest);

    void modifyProject(Authentication authentication, ProjectModifyRequest projectModifyRequest);

    void modifyProjectState(Authentication authentication, ProjectModifyStateRequest projectModifyStateRequest);

    void deleteProject(Authentication authentication, Long projectId);

    void registerMember(Authentication authentication, String memberId);

    Page<ProjectByAccountResponse> getProjectList(Authentication authentication, Pageable pageable);

    Page<TasksResponse> getTaskList(Long projectId, Pageable pageable);

    Page<AccountByProjectResponse> getAccountList(Long projectId, Pageable pageable);
}
