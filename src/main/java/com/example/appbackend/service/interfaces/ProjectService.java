package com.example.appbackend.service.interfaces;

import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Project;

import java.util.List;

public interface ProjectService {

    Project getProject(String name,String username);

    List<Project> getUserProject(String username);

    Project addProject(Project project,String username);
    void addUserToProject(String projectName,String username);

    void addTaskToProject(String taskCode, String projectName);

    List<String> getProjectUsers(String projectName);
    List<String> getUsersNotInProject(String projectName);
    String getProductManagerUsername(String projectName);
    Project setProductManager(String projectName,String productManager);

}
