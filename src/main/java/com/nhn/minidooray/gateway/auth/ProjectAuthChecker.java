package com.nhn.minidooray.gateway.auth;

import com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType;
import com.nhn.minidooray.gateway.service.TaskApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectAuthChecker {
    private final TaskApiService taskApiService;

    public boolean check(HttpServletRequest request, Authentication authentication,
                         ProjectAuthorityType.PermissionType checkPermission,
                         String checkType) {
        String path = request.getServletPath().substring("/project/".length());

        int endIndex = path.indexOf('/');

        if (endIndex < 0) {
            endIndex = path.length();
        }

        int projectId;

        try {
            projectId = Integer.parseInt(path.substring(0, endIndex));
        } catch (NumberFormatException e) {
            log.error("check : {}", e.getMessage(), e);
            return false;
        }

        String id = authentication.getName();

        ProjectAuthorityType projectAuthorityType = taskApiService.getProjectAuthorityType(projectId, id);

        Method method = ReflectionUtils.findMethod(projectAuthorityType.getClass(), "get" + checkType + "Authority");

        if (method == null) {
            return false;
        }

        ReflectionUtils.makeAccessible(method);

        Map<ProjectAuthorityType.PermissionType, Boolean> map = (Map) ReflectionUtils.invokeMethod(method, projectAuthorityType);

        return map.get(checkPermission);
    }
}
