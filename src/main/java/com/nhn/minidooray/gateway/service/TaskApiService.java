package com.nhn.minidooray.gateway.service;

public interface TaskApiService {
    boolean projectAuthorityCheck(Integer projectId, String accountId);
}
