package com.nhn.minidooray.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.project.prefix}/{projectId}${com.nhn.minidooray.mapping.project-account.prefix}")
public class ProjectAccountController {
    @GetMapping("${com.nhn.minidooray.mapping.project-account.list}")
    public String getList(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "project/account/list";
    }

}
