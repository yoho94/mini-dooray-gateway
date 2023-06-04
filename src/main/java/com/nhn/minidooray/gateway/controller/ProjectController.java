package com.nhn.minidooray.gateway.controller;


import com.nhn.minidooray.gateway.domain.request.ProjectCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectModifyRequest;
import com.nhn.minidooray.gateway.service.TaskApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.project-prefix}")
public class ProjectController {
    private final TaskApiService taskApiService;

    @GetMapping
    public String getProjectList() {
        // TODO 목록 조회

        return "project/list";
    }

    @PostMapping("/create")
    public String createProject(Authentication authentication, @Valid ProjectCreateRequest projectCreateRequest,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // TODO 에러 처리
            //            throw new
        }

        Integer projectId = taskApiService.createProject(authentication, projectCreateRequest);

        return "redirect:/project";
    }

    @PostMapping("/modify")
    public String modifyProject(Authentication authentication, @Valid ProjectModifyRequest projectModifyRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO 에러 처리
            //            throw new
        }

        taskApiService.modifyProject(authentication, projectModifyRequest);

        return "redirect:/project";
    }
}
