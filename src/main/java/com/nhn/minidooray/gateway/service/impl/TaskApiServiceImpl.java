package com.nhn.minidooray.gateway.service.impl;

import com.nhn.minidooray.gateway.config.ApiUrlProperties;
import com.nhn.minidooray.gateway.config.ProjectApiMappingProperties;
import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyRequest;
import com.nhn.minidooray.gateway.domain.response.ApiResultResponse;
import com.nhn.minidooray.gateway.domain.response.ProjectAccountResponse;
import com.nhn.minidooray.gateway.domain.response.ProjectResponse;
import com.nhn.minidooray.gateway.exception.NoSuchException;
import com.nhn.minidooray.gateway.service.TaskApiService;
import com.nhn.minidooray.gateway.util.ApiCallUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class TaskApiServiceImpl implements TaskApiService {

    private final RestTemplate restTemplate;
    private final ProjectApiMappingProperties projectApiMappingProperties;
    private final String urlPrefix;

    public TaskApiServiceImpl(RestTemplate restTemplate, ApiUrlProperties apiUrlProperties, ProjectApiMappingProperties projectApiMappingProperties) {
        this.restTemplate = restTemplate;
        this.projectApiMappingProperties = projectApiMappingProperties;

        urlPrefix = apiUrlProperties.getTaskUrl() + projectApiMappingProperties.getPrefix();
    }

    @Override
    public ProjectAuthorityType getProjectAuthorityType(Long projectId, String accountId) {
        ApiResultResponse<ProjectAccountResponse> apiResultResponse = ApiCallUtil.get(new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getGetAccount(), projectId, accountId);

        if (apiResultResponse.isEmpty()) {
            throw new NoSuchException("ProjectAccountResponse Not Found");
        }

        ProjectAccountResponse projectAccountResponse = apiResultResponse.getFirst();
        String code = projectAccountResponse.getCode();

        if (!StringUtils.hasText(code)) {
            throw new NoSuchException("ProjectAccountResponse.code Not Found");
        }

        return ProjectAuthorityType.valueOfCode(code);
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
//        ApiResultResponse<Page<ProjectResponse>> apiResultResponse = ApiCallUtil.get(new ParameterizedTypeReference<>() {
//        }, restTemplate, urlPrefix + projectApiMappingProperties.getGetAccounts(), );
//
//        if (apiResultResponse.isEmpty()) {
//            throw new WebException("API ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return apiResultResponse.getFirst();
        return null;
    }
}
