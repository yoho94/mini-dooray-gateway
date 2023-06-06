package com.nhn.minidooray.gateway.service.impl;

import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyRequest;
import com.nhn.minidooray.gateway.domain.response.ProjectResponse;
import com.nhn.minidooray.gateway.service.TaskApiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TaskApiServiceImpl implements TaskApiService {
    @Override
    public ProjectAuthorityType getProjectAuthorityType(Long projectId, String accountId) {
        return ProjectAuthorityType.ADMIN;
    }

    @Override
    public Long createProject(Authentication authentication, ProjectCreateRequest projectCreateRequest) {
        return null;
    }

    @Override
    public void modifyProject(Authentication authentication, ProjectModifyRequest projectModifyRequest) {

    }

    @Override
    public void deleteProject(Authentication authentication, Long projectId) {

    }

    @Override
    public void registerMember(Authentication authentication, String memberId) {

    }

    @Override
    public Page<ProjectResponse> getProjectList(Authentication authentication, Pageable pageable) {
        return null;
    }
}
