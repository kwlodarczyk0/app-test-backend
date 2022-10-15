package com.example.appbackend.service;


import com.example.appbackend.domain.AppUser;
import com.example.appbackend.domain.Project;
import com.example.appbackend.domain.Role;
import com.example.appbackend.domain.Task;
import com.example.appbackend.repository.AppUserRepository;
import com.example.appbackend.repository.ProjectRepository;
import com.example.appbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;

    private final TaskRepository taskRepository;

    @Override
    public Project getProject(String name) throws RuntimeException {
        Project project =  projectRepository.findByName(name);
        if(project!=null){
            return project;
        } else {
            throw new RuntimeException("Project does not exist");
        }
    }

    @Override
    public List<Project> getUserProject(Long id) {
        return projectRepository.findProjectsByUsersId(id);
    }

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(String projectName, String username) {
        //log.info("Adding new role: {} to user: {}",roleName,username);
        AppUser appUser = appUserRepository.findByUsername(username);
        Project project = projectRepository.findByName(projectName);
        project.getUsers().add(appUser);
    }

    @Override
    public void addTaskToProject(String taskCode, String projectName) {
        Project project = projectRepository.findByName(projectName);
        Task task = taskRepository.findTaskByCode(taskCode);
        project.getTasks().add(task);
    }


}
