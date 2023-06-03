package com.nhn.minidooray.gateway.auth;

import com.nhn.minidooray.gateway.service.TaskApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectAuthChecker {
    private final TaskApiService taskApiService;

    public boolean check(HttpServletRequest request, Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken) { // 미 로그인
            return false;
        }

        String path = request.getServletPath().substring("/project".length());

        if (path.length() <= 1) { // project list
            return true;
        }

        int startIndex = path.indexOf('/');
        int endIndex = path.lastIndexOf('/');

        if (startIndex == endIndex) {
            endIndex = path.length();
        }

        int projectId;

        try {
            projectId = Integer.parseInt(path.substring(startIndex + 1, endIndex));
        } catch (NumberFormatException e) {
            log.error("check : {}", e.getMessage(), e);
            return false;
        }

        String id = authentication.getName();

        return taskApiService.projectAuthorityCheck(projectId, id);
    }
}
