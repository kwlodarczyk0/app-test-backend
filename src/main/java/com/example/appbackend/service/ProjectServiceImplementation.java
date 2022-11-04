package com.example.appbackend.service;


import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Project;
import com.example.appbackend.model.Task;
import com.example.appbackend.repository.AppUserRepository;
import com.example.appbackend.repository.ProjectRepository;
import com.example.appbackend.repository.TaskRepository;
import com.example.appbackend.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Project getProject(String name,String username) throws RuntimeException  {
        Project project =  projectRepository.findByName(name);
        AppUser appUser = appUserRepository.findByUsername(username);

        boolean isUserInProject = false;

        if(project==null) throw new RuntimeException("Project does not exist");

        for (AppUser user: project.getUsers()) {
            if(user.getUsername().equals(appUser.getUsername())){
                isUserInProject = true;
                break;
            }
        }

        if(!isUserInProject) throw new RuntimeException("user doesn't have access to this project");
        return project;
    }

    @Override
    public List<Project> getUserProject(String username) {
        return projectRepository.findProjectsByUsersUsername(username);
    }



    @Override
    public Project addProject(Project project,String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        projectRepository.save(project);
        addUserToProject(project.getName(),appUser.getUsername());
        return project;
    }

    @Override
    public void addUserToProject(String projectName, String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        Project project = projectRepository.findByName(projectName);

        if(appUser==null){
            log.error("User not found in the db");
            throw new UsernameNotFoundException("User not found in the db");
        }

        project.getUsers().add(appUser);
    }

    @Override
    public void addTaskToProject(String taskCode, String projectName) {
        Project project = projectRepository.findByName(projectName);
        Task task = taskRepository.findTaskByCode(taskCode);
        project.getTasks().add(task);
    }
    @Override
    public List<String> getProjectUsers(String projectName) {
        return projectRepository.getProjectUsers(projectName);
    }

    @Override
    public List<String> getUsersNotInProject(String projectName){
        return  projectRepository.getUsersAreNotInProject(projectName);
    }

    @Override
    public String getProductManagerUsername(String projectName) {
        return projectRepository.findProductManager(projectName);
    }

    @Override
    public Project setProductManager(String projectName,String productManager) {
        Project project =  projectRepository.findByName(projectName);
        AppUser user = appUserRepository.findByUsername(productManager);//if null throw exception
        project.setProductManager(productManager);
        project.getUsers().add(user);
        return project;

    }


}
