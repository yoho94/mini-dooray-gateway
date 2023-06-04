package com.nhn.minidooray.gateway.service.impl.temp;

import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyRequest;
import com.nhn.minidooray.gateway.service.TaskApiService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TempTaskApiService implements TaskApiService {
    @Override
    public ProjectAuthorityType getProjectAuthorityType(Integer projectId, String accountId) {
        return ProjectAuthorityType.ADMIN;
    }

    @Override
    public Integer createProject(Authentication authentication, ProjectCreateRequest projectCreateRequest) {
        return null;
    }

    @Override
    public void modifyProject(Authentication authentication, ProjectModifyRequest projectModifyRequest) {

    }

    @Override
    public void registerMember(Authentication authentication, String memberId) {

    }
}
