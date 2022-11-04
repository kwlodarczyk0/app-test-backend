package com.example.appbackend.service;

import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Project;
import com.example.appbackend.model.Task;
import com.example.appbackend.repository.AppUserRepository;
import com.example.appbackend.repository.ProjectRepository;
import com.example.appbackend.repository.TaskRepository;
import com.example.appbackend.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImplementation implements TaskService {
    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Task getTask(String code) {
        return taskRepository.findTaskByCode(code);
    }

    @Override
    public Task addTask(Task task,String projectName) {
        Project project = projectRepository.findByName(projectName);
        task.setStatus("OPEN");
        task.setCode(projectName+"-"+project.getTasks().toArray().length);
        task.setCreationDate(new Date());
        taskRepository.save(task);
        project.getTasks().add(task);
        return task;
    }

    @Override
    public Task addUserToTask(String username, String taskCode) {
       AppUser user =  appUserRepository.findByUsername(username);
       Task task = taskRepository.findTaskByCode(taskCode);
       task.setUser(user);
       return task;
    }

    @Override
    public Task changeTaskStatus(String code,String status) {
        Task task = taskRepository.findTaskByCode(code);
        task.setStatus(status);
        return task;
    }


}
