package com.nhn.minidooray.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.mapping.api.task")
@Getter
@Setter
public class ProjectApiMappingProperties {
    private String prefix;
    private String getProjectsByAccountId;
    private String createProject;
    private String updateProject;
    private String deleteProject;
    private String getProject;
    private String getProjects;
    private String createAccount;
    private String updateAccount;
    private String deleteAccount;
    private String getAccount;
    private String getAccounts;
    private String createMilestone;
    private String updateMilestone;
    private String deleteMilestone;
    private String getMilestone;
    private String getMilestones;
    private String createTag;
    private String updateTag;
    private String deleteTag;
    private String getTag;
    private String getTags;
    private String createTask;
    private String updateTask;
    private String deleteTask;
    private String getTask;
    private String getTasks;
    private String createTasktag;
    private String updateTasktag;
    private String deleteTasktag;
    private String getTasktag;
    private String getTasktags;
}
