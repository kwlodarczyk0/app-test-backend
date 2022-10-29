package com.example.appbackend.config;

import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Project;
import com.example.appbackend.model.Role;
import com.example.appbackend.model.Task;
import com.example.appbackend.service.interfaces.AppUserService;
import com.example.appbackend.service.interfaces.ProjectService;
import com.example.appbackend.service.interfaces.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Configuration
public class StartConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(AppUserService appUserService, ProjectService projectService, TaskService taskService){
        return args -> {
            appUserService.saveRole(new Role(null,"ROLE_USER"));
            appUserService.saveRole(new Role(null,"ROLE_MANAGER"));
            appUserService.saveRole(new Role(null,"ROLE_ADMIN"));
            appUserService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

            appUserService.saveUser(new AppUser(null,"Hohn","test","1234",new ArrayList<>()));
            appUserService.addRoleToAppUser("test","ROLE_USER");

            appUserService.saveUser(new AppUser(null,"Hohn","krystian","1234",new ArrayList<>()));
            appUserService.addRoleToAppUser("krystian","ROLE_USER");


            projectService.addProject(new Project(null,"Essilor",new ArrayList<>(),new ArrayList<>()),"test");
            projectService.addProject(new Project(null,"KESKO",new ArrayList<>(),new ArrayList<>()),"test");


            taskService.addTask(new Task(null,"ESIINT-510","OPEN",null,null));
            taskService.addTask(new Task(null,"ESIINT-512","IN_PROGRESS",null,null));
            taskService.addTask(new Task(null,"ESIINT-511","DONE",null,null));

            taskService.addUserToTask("test","ESIINT-510");

            projectService.addTaskToProject("ESIINT-510","Essilor");
            projectService.addTaskToProject("ESIINT-511","Essilor");
            projectService.addTaskToProject("ESIINT-512","Essilor");



        };
    }
}
