package com.example.appbackend.api;

import com.example.appbackend.api.dto.UserToProject;
import com.example.appbackend.domain.AppUser;
import com.example.appbackend.domain.Project;
import com.example.appbackend.service.ProjectService;
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
        return ResponseEntity.ok().body(projectService.getProject(name));
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



}
