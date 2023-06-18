package com.nhn.minidooray.gateway.service.impl;

import com.nhn.minidooray.gateway.config.ApiUrlProperties;
import com.nhn.minidooray.gateway.config.ProjectApiMappingProperties;
import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.domain.enums.ProjectStateType;
import com.nhn.minidooray.gateway.domain.request.*;
import com.nhn.minidooray.gateway.domain.response.*;
import com.nhn.minidooray.gateway.exception.ApiException;
import com.nhn.minidooray.gateway.exception.NoSuchException;
import com.nhn.minidooray.gateway.service.AccountApiService;
import com.nhn.minidooray.gateway.service.TaskApiService;
import com.nhn.minidooray.gateway.util.ApiCallUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TaskApiServiceImpl implements TaskApiService {

    private final RestTemplate restTemplate;
    private final ProjectApiMappingProperties projectApiMappingProperties;
    private final String urlPrefix;
    private final AccountApiService accountApiService;

    public TaskApiServiceImpl(RestTemplate restTemplate, ApiUrlProperties apiUrlProperties, ProjectApiMappingProperties projectApiMappingProperties, AccountApiService accountApiService) {
        this.restTemplate = restTemplate;
        this.projectApiMappingProperties = projectApiMappingProperties;
        this.accountApiService = accountApiService;

        urlPrefix = apiUrlProperties.getTaskUrl() + projectApiMappingProperties.getPrefix();
    }

    @Override
    public ProjectAuthorityType getProjectAuthorityType(Long projectId, String accountId) {
        ApiResultResponse<ProjectAccountResponse> apiResultResponse = ApiCallUtil.get(new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getGetAccount(), projectId, accountId);

        if (apiResultResponse.isEmpty()) {
            return ProjectAuthorityType.NONE;
        }

        ProjectAccountResponse projectAccountResponse = apiResultResponse.getFirst();
        String code = projectAccountResponse.getCode();

        if (!StringUtils.hasText(code)) {
            throw new NoSuchException("ProjectAccountResponse.code Not Found");
        }

        return ProjectAuthorityType.valueOfCode(code);
    }

    @Override
    public ProjectStateType getProjectStateType(Long projectId) {
        ApiResultResponse<ProjectResponse> apiResultResponse = ApiCallUtil.get(new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getGetProject(), projectId);

        if (apiResultResponse.isEmpty()) {
            throw new ApiException();
        }

        ProjectResponse projectAccountResponse = apiResultResponse.getFirst();
        String code = projectAccountResponse.getStateCode();

        if (!StringUtils.hasText(code)) {
            throw new NoSuchException("ProjectStateType.code Not Found");
        }

        return ProjectStateType.valueOfCode(code);
    }

    @Override
    public void createProject(Authentication authentication, ProjectCreateRequest projectCreateRequest) {

        projectCreateRequest.setAccountId(authentication.getName());
        ApiCallUtil.callWithBody(HttpMethod.POST, new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getCreateProject(), projectCreateRequest);
    }

    @Override
    public void modifyProject(Authentication authentication, ProjectModifyRequest projectModifyRequest) {
        ApiCallUtil.callWithBody(HttpMethod.PUT, new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getUpdateProject(), projectModifyRequest, projectModifyRequest.getProjectId());

    }

    @Override
    public void modifyProjectState(Authentication authentication, ProjectModifyStateRequest projectModifyStateRequest) {

    }

    @Override
    public void deleteProject(Authentication authentication, Long projectId) {

    }

    @Override
    public void registerMember(Authentication authentication, String memberId) {

    }

    @Override
    public Page<ProjectByAccountResponse> getProjectList(Authentication authentication, Pageable pageable) {
        ApiResultResponse<RestPageImpl<ProjectByAccountResponse>> apiResultResponse = ApiCallUtil.get(new ParameterizedTypeReference<>() {
                                                                                                      }, restTemplate, urlPrefix + projectApiMappingProperties.getGetProjectsByAccountId() + "?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                authentication.getName());

        if (apiResultResponse.isEmpty()) {
            throw new ApiException();
        }

        return apiResultResponse.getFirst();
    }


    @Override
    public Page<TasksResponse> getTaskList(Long projectId, Pageable pageable) {
        ApiResultResponse<RestPageImpl<TasksResponse>> apiResultResponse =
                ApiCallUtil.get(new ParameterizedTypeReference<>() {
                                }, restTemplate, urlPrefix + projectApiMappingProperties.getGetTasks() + "?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        projectId);

        if (apiResultResponse.isEmpty()) {
            throw new ApiException();
        }

        return apiResultResponse.getFirst();
    }

    @Override
    public Page<AccountByProjectResponse> getAccountList(Long projectId, Pageable pageable) {
        ApiResultResponse<RestPageImpl<AccountByProjectResponse>> apiResultResponse =
                ApiCallUtil.get(new ParameterizedTypeReference<>() {
                                }, restTemplate, urlPrefix + projectApiMappingProperties.getGetAccounts() + "?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        projectId);

        if (apiResultResponse.isEmpty()) {
            throw new ApiException();
        }

        RestPageImpl<AccountByProjectResponse> page = apiResultResponse.getFirst();

        String[] array = page.getContent().stream()
                .map(AccountByProjectResponse::getAccountId)
                .toArray(String[]::new);

        List<Account> accounts = accountApiService.getAccountsById(array);

        return page.map(accountByProjectResponse -> {
            accounts.stream()
                    .filter(account -> account.getId().equals(accountByProjectResponse.getAccountId()))
                    .findAny()
                    .ifPresent(account -> accountByProjectResponse.setAccountName(account.getName()));

            return accountByProjectResponse;
        });
    }

    @Override
    public void writeAccount(Long projectId, ProjectAccountCreateRequest projectAccountCreateRequest) {
        ApiCallUtil.callWithBody(HttpMethod.POST, new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getCreateAccount(), projectAccountCreateRequest, projectId);
    }

    @Override
    public void modifyAccount(Long projectId, String accountId, ProjectAccountModifyRequest projectAccountModifyRequest) {
        ApiCallUtil.callWithBody(HttpMethod.PUT, new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getUpdateAccount(), projectAccountModifyRequest, projectId, accountId);
    }

    @Override
    public void deleteAccount(Long projectId, String accountId) {
        ApiCallUtil.call(HttpMethod.DELETE, new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getDeleteAccount(), projectId, accountId);
    }

    @Override
    public TaskResponse getTask(Long projectId, Long taskId) {
        ApiResultResponse<TaskResponse> apiResultResponse = ApiCallUtil.call(HttpMethod.GET, new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getGetTask(), projectId, taskId);

        if (apiResultResponse.isEmpty()) {
            throw new ApiException();
        }

        return apiResultResponse.getFirst();
    }

    @Override
    public Long writeTask(Long projectId, TaskFormRequest taskFormRequest) {
        ApiResultResponse<LongIdResponse> response = ApiCallUtil.callWithBody(HttpMethod.POST, new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getCreateTask(), taskFormRequest, projectId);

        if (response.isEmpty()) {
            throw new ApiException();
        }

        return response.getFirst().getId();
    }

    @Override
    public Long modifyTask(Long projectId, TaskFormRequest taskFormRequest) {
        ApiResultResponse<LongIdResponse> response = ApiCallUtil.callWithBody(HttpMethod.PUT, new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getUpdateTask(), taskFormRequest, projectId, taskFormRequest.getTaskId());

        if (response.isEmpty()) {
            throw new ApiException();
        }

        return response.getFirst().getId();
    }

    @Override
    public void deleteTask(Long projectId, Long taskId) {
        ApiCallUtil.call(HttpMethod.DELETE, new ParameterizedTypeReference<ApiResultResponse<Long>>() {
        }, restTemplate, urlPrefix + projectApiMappingProperties.getDeleteTask(), projectId, taskId);
    }

    @Override
    public List<MilestoneByProjectResponse> getAllMileStone(Long projectId) {
        return ApiCallUtil.call(HttpMethod.GET, new ParameterizedTypeReference<ApiResultResponse<MilestoneByProjectResponse>>() {
                }, restTemplate, urlPrefix + projectApiMappingProperties.getGetAllMileStones(), projectId)
                .getResult();
    }
}
