package com.nhn.minidooray.gateway.service;

import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.domain.enums.ProjectStateType;
import com.nhn.minidooray.gateway.domain.request.*;
import com.nhn.minidooray.gateway.domain.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TaskApiService {
    ProjectAuthorityType getProjectAuthorityType(Long projectId, String accountId);

    ProjectStateType getProjectStateType(Long projectId);

    void createProject(Authentication authentication, ProjectCreateRequest projectCreateRequest);

    void modifyProject(Authentication authentication, ProjectModifyRequest projectModifyRequest);

    void modifyProjectState(Authentication authentication, ProjectModifyStateRequest projectModifyStateRequest);

    void deleteProject(Authentication authentication, Long projectId);

    void registerMember(Authentication authentication, String memberId);

    Page<ProjectByAccountResponse> getProjectList(Authentication authentication, Pageable pageable);

    Page<AccountByProjectResponse> getAccountList(Long projectId, Pageable pageable);

    void writeAccount(Long projectId, ProjectAccountCreateRequest projectAccountCreateRequest);

    void modifyAccount(Long projectId, String accountId, ProjectAccountModifyRequest projectAccountModifyRequest);

    void deleteAccount(Long projectId, String accountId);

    Page<TasksResponse> getTaskList(Long projectId, Pageable pageable);

    TaskResponse getTask(Long projectId, Long taskId);

    Long writeTask(Long projectId, TaskFormRequest taskFormRequest);

    Long modifyTask(Long projectId, TaskFormRequest taskFormRequest);

    void deleteTask(Long projectId, Long taskId);

    List<MilestoneByProjectResponse> getAllMileStone(Long projectId);
}
