package com.nhn.minidooray.gateway.service;

import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyRequest;
import org.springframework.security.core.Authentication;

public interface TaskApiService {
    ProjectAuthorityType getProjectAuthorityType(Integer projectId, String accountId);

    Integer createProject(Authentication authentication, ProjectCreateRequest projectCreateRequest);

    void modifyProject(Authentication authentication, ProjectModifyRequest projectModifyRequest);

    void registerMember(Authentication authentication, String memberId);
}
