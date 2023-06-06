package com.nhn.minidooray.gateway.service;

import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyRequest;
import com.nhn.minidooray.gateway.domain.response.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface TaskApiService {
    ProjectAuthorityType getProjectAuthorityType(Long projectId, String accountId);

    Long createProject(Authentication authentication, ProjectCreateRequest projectCreateRequest);

    void modifyProject(Authentication authentication, ProjectModifyRequest projectModifyRequest);

    void deleteProject(Authentication authentication, Long projectId);

    void registerMember(Authentication authentication, String memberId);

    Page<ProjectResponse> getProjectList(Authentication authentication, Pageable pageable);
}
