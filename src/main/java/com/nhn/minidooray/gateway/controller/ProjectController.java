package com.nhn.minidooray.gateway.controller;


import com.nhn.minidooray.gateway.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyRequest;
import com.nhn.minidooray.gateway.domain.response.ProjectResponse;
import com.nhn.minidooray.gateway.exception.RequiredValueException;
import com.nhn.minidooray.gateway.exception.ValidationFailedException;
import com.nhn.minidooray.gateway.service.TaskApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.project.prefix}")
public class ProjectController {
    public static final String REDIRECT_PROJECT = "redirect:/project";
    private final TaskApiService taskApiService;

    @GetMapping("${com.nhn.minidooray.mapping.project-account.list}")
    public String getProjectList(Authentication authentication, Model model, @PageableDefault Pageable pageable) {
        Page<ProjectResponse> page = taskApiService.getProjectList(authentication, pageable);

        model.addAttribute("projectList", page.getContent());
        model.addAttribute("totalCount", page.getTotalElements());
        model.addAttribute("pageCount", page.getTotalPages());

        return "project/list";
    }

    @PostMapping("${com.nhn.minidooray.mapping.project.create}")
    public String createProject(Authentication authentication, @Valid ProjectCreateRequest projectCreateRequest,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        taskApiService.createProject(authentication, projectCreateRequest);

        return REDIRECT_PROJECT;
    }

    @PostMapping("${com.nhn.minidooray.mapping.project.modify}")
    public String modifyProject(Authentication authentication, @Valid ProjectModifyRequest projectModifyRequest,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        taskApiService.modifyProject(authentication, projectModifyRequest);

        return REDIRECT_PROJECT;
    }

    @GetMapping("${com.nhn.minidooray.mapping.project.delete}")
    public String deleteProject(Authentication authentication, @RequestParam(value = "projectId", required = false) Long projectId) {

        // todo 아래 로직 서비스로 옮기기
        if (projectId == null) {
            throw new RequiredValueException("projectId is Null");
        }

        if (projectId <= 0) {
            throw new RequiredValueException("projectId는 0보다 커야 합니다.");
        }

        taskApiService.deleteProject(authentication, projectId);

        return REDIRECT_PROJECT;
    }
}
