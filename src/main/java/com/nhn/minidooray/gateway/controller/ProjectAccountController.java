package com.nhn.minidooray.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.project-prefix}/{projectId}${com.nhn.minidooray.project-account.mapping.prefix}")
public class ProjectAccountController {
    @GetMapping("${com.nhn.minidooray.project-account.mapping.list}")
    public String getList(@PathVariable Integer projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "project/account/list";
    }

}
