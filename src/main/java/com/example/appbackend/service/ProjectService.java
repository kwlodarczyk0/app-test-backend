package com.example.appbackend.service;

import com.example.appbackend.domain.Project;

import java.util.List;

public interface ProjectService {

    Project getProject(String name);

    List<Project> getUserProject(String username);

    Project addProject(Project project);
    void addUserToProject(String projectName,String username);

    void addTaskToProject(String taskCode, String projectName);


}
