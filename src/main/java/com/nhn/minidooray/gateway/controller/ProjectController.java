package com.nhn.minidooray.gateway.controller;


import com.nhn.minidooray.gateway.service.TaskApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final TaskApiService taskApiService;

    @GetMapping
    public String getProjectList() {
        return "project/list";
    }

    @GetMapping("/{projectId}")
    public String getTaskList(@PathVariable Integer projectId) {
        return "task/list";
    }

    @GetMapping("/{projectId}/{taskId}")
    public String getTask(@PathVariable Integer projectId, @PathVariable Integer taskId) {
        return "task/list";
    }
}
