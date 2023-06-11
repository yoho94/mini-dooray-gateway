package com.nhn.minidooray.gateway.controller;

import com.nhn.minidooray.gateway.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.gateway.service.AccountApiService;
import com.nhn.minidooray.gateway.service.TaskApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.project.prefix}/{projectId}${com.nhn.minidooray.mapping.project-account.prefix}")
public class ProjectAccountController {

    private final TaskApiService taskApiService;
    private final AccountApiService accountApiService;

    @GetMapping("${com.nhn.minidooray.mapping.project-account.list}")
    public String getList(@PathVariable Long projectId, Model model, @PageableDefault Pageable pageable) {
        Page<AccountByProjectResponse> page = taskApiService.getAccountList(projectId, pageable);

        model.addAttribute("page", page);
        model.addAttribute("projectAccountList", page.getContent());
        model.addAttribute("totalCount", page.getTotalElements());
        model.addAttribute("pageCount", page.getTotalPages());

        model.addAttribute(projectId);
        return "project/account/list";
    }

    @GetMapping("${com.nhn.minidooray.mapping.project-account.read}")
    public String getAccount(@PathVariable Long projectId, Model model) {
        model.addAttribute(projectId);
        return "project/account/list";
    }

    @PostMapping("${com.nhn.minidooray.mapping.project-account.write}")
    public String writeAccount(@PathVariable Long projectId, Model model) {
        model.addAttribute(projectId);
        return "project/account/list";
    }

    @PostMapping("${com.nhn.minidooray.mapping.project-account.modify}")
    public String modifyAccount(@PathVariable Long projectId, Model model) {
        model.addAttribute(projectId);
        return "project/account/list";
    }

    @PostMapping("${com.nhn.minidooray.mapping.project-account.delete}")
    public String deleteAccount(@PathVariable Long projectId, Model model) {
        model.addAttribute(projectId);
        return "project/account/list";
    }

}
