package com.nhn.minidooray.gateway.service.impl.temp;

import com.nhn.minidooray.gateway.service.TaskApiService;
import org.springframework.stereotype.Service;

@Service
public class TempTaskApiService implements TaskApiService {
    @Override
    public boolean projectAuthorityCheck(Integer projectId, String accountId) {
        return true;
    }
}
