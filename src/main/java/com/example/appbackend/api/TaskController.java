package com.example.appbackend.api;

import com.example.appbackend.domain.Task;
import com.example.appbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/task/getTask/{code}")
    public ResponseEntity<Task> getProject(@PathVariable String code){
        return ResponseEntity.ok().body(taskService.getTask(code));
    }



}
