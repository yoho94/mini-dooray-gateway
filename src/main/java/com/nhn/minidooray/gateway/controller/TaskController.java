package com.nhn.minidooray.gateway.controller;

import com.nhn.minidooray.gateway.config.ProjectMappingProperties;
import com.nhn.minidooray.gateway.config.TaskMappingProperties;
import com.nhn.minidooray.gateway.domain.request.TaskFormRequest;
import com.nhn.minidooray.gateway.domain.response.MilestoneByProjectResponse;
import com.nhn.minidooray.gateway.domain.response.TaskResponse;
import com.nhn.minidooray.gateway.domain.response.TasksResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.project.prefix}/{projectId}${com.nhn.minidooray.mapping.task.prefix}")
public class TaskController {
    private final TaskApiService taskApiService;

    private final ProjectMappingProperties projectMappingProperties;
    private final TaskMappingProperties taskMappingProperties;

    @GetMapping("${com.nhn.minidooray.mapping.task.list}")
    public String getTaskList(Model model, @PageableDefault Pageable pageable, @PathVariable Long projectId) {
        Page<TasksResponse> page = taskApiService.getTaskList(projectId, pageable);

        model.addAttribute("page", page);
        model.addAttribute("taskList", page.getContent());
        model.addAttribute("totalCount", page.getTotalElements());
        model.addAttribute("pageCount", page.getTotalPages());

        return "project/task/list";
    }

    @GetMapping("/{taskId}${com.nhn.minidooray.mapping.task.read}")
    public String getTask(@PathVariable Long projectId,
                          @PathVariable Long taskId,
                          Model model) {
        TaskResponse task = taskApiService.getTask(projectId, taskId);

        model.addAttribute("task", task);

        return "project/task/read";
    }

    @GetMapping("${com.nhn.minidooray.mapping.task.write}")
    public String getCreateTaskForm(@PathVariable Long projectId,
                                    Model model) {

        model.addAttribute(projectId);
        model.addAttribute("task", new TaskFormRequest());

        List<MilestoneByProjectResponse> allMileStone = taskApiService.getAllMileStone(projectId);
        model.addAttribute("mileStoneList", allMileStone);

        return "project/task/form";
    }

    @PostMapping("${com.nhn.minidooray.mapping.task.write}")
    public String createTask(@PathVariable Long projectId,
                             Authentication authentication,
                             @Valid TaskFormRequest taskFormRequest,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        taskFormRequest.setWriterId(authentication.getName());
        Long taskId = taskApiService.writeTask(projectId, taskFormRequest);

        return redirectTaskRead(projectId, taskId);
    }

    @GetMapping("/{taskId}${com.nhn.minidooray.mapping.task.modify}")
    public String getModifyTaskForm(@PathVariable Long projectId,
                                    @PathVariable Long taskId,
                                    Model model) {

        TaskResponse task = taskApiService.getTask(projectId, taskId);

        model.addAttribute(projectId);
        model.addAttribute("task", task);

        List<MilestoneByProjectResponse> allMileStone = taskApiService.getAllMileStone(projectId);
        model.addAttribute("mileStoneList", allMileStone);

        return "project/task/form";
    }

    @PostMapping("/{taskId}${com.nhn.minidooray.mapping.task.modify}")
    public String modifyTask(@PathVariable Long projectId,
                             @PathVariable Long taskId,
                             Authentication authentication,
                             @Valid TaskFormRequest taskFormRequest,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        taskFormRequest.setTaskId(taskId);
        taskFormRequest.setWriterId(authentication.getName());
        taskApiService.modifyTask(projectId, taskFormRequest);

        return redirectTaskRead(projectId, taskId);
    }

    @GetMapping("/{taskId}${com.nhn.minidooray.mapping.task.delete}")
    public String deleteTask(@PathVariable Long projectId,
                             @PathVariable Long taskId) {

        taskApiService.deleteTask(projectId, taskId);

        return redirectTaskList(projectId);
    }

    private String redirectTaskList(Long projectId) {
        return "redirect:"
                + projectMappingProperties.getPrefix() + "/" + projectId
                + taskMappingProperties.getPrefix()
                + taskMappingProperties.getList();
    }

    private String redirectTaskRead(Long projectId, Long taskId) {
        return "redirect:"
                + projectMappingProperties.getPrefix() + "/" + projectId
                + taskMappingProperties.getPrefix() + "/" + taskId
                + taskMappingProperties.getRead();
    }
}
