package com.example.appbackend.controller;

import com.example.appbackend.controller.dto.UserToProject;
import com.example.appbackend.model.Project;
import com.example.appbackend.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/project/getProject/{name}")
    public ResponseEntity<Project> getProject(@PathVariable String name){
        //add configuration that if actuall user is not in project he is not able to see project details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(projectService.getProject(name,auth.getPrincipal().toString()));
    }

    @GetMapping("/project/user-projects")
    public ResponseEntity<List<Project>> getUserProject(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(projectService.getUserProject(auth.getPrincipal().toString()));
    }

    @PostMapping("/project/add/add-user-to-project")
    public void addUserToProject(@RequestBody UserToProject user){
        projectService.addUserToProject(user.getProjectName(),user.getUsername());
    }

    @PostMapping("/project/add/add-project")
    public Project addProject(@RequestBody Project project){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return projectService.addProject(project,auth.getPrincipal().toString());
    }



}
