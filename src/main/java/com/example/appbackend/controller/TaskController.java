package com.example.appbackend.controller;

import com.example.appbackend.controller.dto.ChangeTaskStatus;
import com.example.appbackend.model.Task;
import com.example.appbackend.service.interfaces.ProjectService;
import com.example.appbackend.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;

    @GetMapping("/task/getTask/{code}")
    public ResponseEntity<Task> getProject(@PathVariable String code){
        return ResponseEntity.ok().body(taskService.getTask(code));
    }


    @PutMapping("/task/updateTaskStatus")
    public Task updateTask(@RequestBody ChangeTaskStatus changeTaskStatus){
        return taskService.changeTaskStatus(changeTaskStatus.getTaskCode(), changeTaskStatus.getStatus());
    }

    @PostMapping("/task/addTask/{projectName}")
    public Task addTask(@RequestBody Task task,@PathVariable String projectName){
        taskService.addTask(task);
        projectService.addTaskToProject(task.getCode(), projectName);
        return task;
    }



}
