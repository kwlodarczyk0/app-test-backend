package com.example.appbackend.service;

import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Task;
import com.example.appbackend.repository.AppUserRepository;
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

    @Override
    public Task getTask(String code) {
        return taskRepository.findTaskByCode(code);
    }

    @Override
    public Task addTask(Task task) {
        task.setCreationDate(new Date());
        return taskRepository.save(task);
    }

    @Override
    public void addUserToTask(String username, String taskCode) {
       AppUser user =  appUserRepository.findByUsername(username);
       Task task = taskRepository.findTaskByCode(taskCode);
       task.setUser(user);
    }

    @Override
    public Task changeTaskStatus(String code,String status) {
        Task task = taskRepository.findTaskByCode(code);
        task.setStatus(status);
        return task;
    }


}
