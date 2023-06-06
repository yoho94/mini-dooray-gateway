package com.nhn.minidooray.gateway.service.impl;

import com.nhn.minidooray.gateway.config.AccountApiMappingProperties;
import com.nhn.minidooray.gateway.config.ApiUrlProperties;
import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.domain.enums.AccountStateType;
import com.nhn.minidooray.gateway.domain.request.AccountStateCreateRequest;
import com.nhn.minidooray.gateway.domain.response.ApiResultResponse;
import com.nhn.minidooray.gateway.exception.NoSuchException;
import com.nhn.minidooray.gateway.service.AccountApiService;
import com.nhn.minidooray.gateway.util.ApiCallUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountApiServiceImpl implements AccountApiService {

    private final RestTemplate restTemplate;
    private final AccountApiMappingProperties accountApiMappingProperties;
    private final String urlPrefix;

    public AccountApiServiceImpl(RestTemplate restTemplate, ApiUrlProperties apiUrlProperties, AccountApiMappingProperties accountApiMappingProperties) {
        this.restTemplate = restTemplate;
        this.accountApiMappingProperties = accountApiMappingProperties;

        urlPrefix = apiUrlProperties.getAccountUrl() + accountApiMappingProperties.getPrefix();
    }

    @Override
    public Account getAccountById(String id) {
        ApiResultResponse<Account> resultResponse = ApiCallUtil.get(new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + accountApiMappingProperties.getReadAccountById(), id);

        if (resultResponse.getTotalCount() != 1) {
            throw new NoSuchException("Account Not Found");
        }

        return resultResponse.getResult().get(0);
    }

    @Override
    public Account getAccountByEmail(String email) {
        ApiResultResponse<Account> resultResponse = ApiCallUtil.get(new ParameterizedTypeReference<>() {
        }, restTemplate, urlPrefix + accountApiMappingProperties.getReadAccountByEmail(), email);

        if (resultResponse.getTotalCount() != 1) {
            throw new NoSuchException("Account Not Found");
        }

        return resultResponse.getResult().get(0);
    }

    @Override
    public void addAccount(Account account) {
        ApiCallUtil.postWithBody(new ParameterizedTypeReference<ApiResultResponse<Void>>() {
        }, restTemplate, urlPrefix + accountApiMappingProperties.getCreateAccount(), account);
    }

    @Override
    public void addAccountState(String id, AccountStateType accountStateType) {
        ApiCallUtil.postWithBody(new ParameterizedTypeReference<ApiResultResponse<Void>>() {
        }, restTemplate, urlPrefix + accountApiMappingProperties.getCreateAccountState(), AccountStateCreateRequest.builder()
                .accountId(id)
                .accountStateCode(accountStateType.getCode())
                .build());
    }

    @Override
    public void updateAccountLastLoginAt(Authentication authentication) {
        ApiCallUtil.get(new ParameterizedTypeReference<ApiResultResponse<Void>>() {
        }, restTemplate, urlPrefix + accountApiMappingProperties.getUpdateAccountLastLoginAt(), authentication.getName());
    }

}
