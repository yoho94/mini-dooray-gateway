package com.nhn.minidooray.gateway.auth;

import com.nhn.minidooray.gateway.service.TaskApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class ProjectAuthChecker {
    private final TaskApiService taskApiService;

    public boolean check(HttpServletRequest request, Authentication authentication) {
        // TODO path 를 /pro/11/... 일 경우 11 만 가져오도록
        String path = request.getServletPath().substring("/project/".length());
        String id = authentication.getName();

        boolean check = taskApiService.projectAuthorityCheck();

        return false;
    }
}
