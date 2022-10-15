package com.example.appbackend.service;

import com.example.appbackend.domain.AppUser;
import com.example.appbackend.domain.Task;
import com.example.appbackend.repository.AppUserRepository;
import com.example.appbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImplementation implements TaskService{

    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public Task getTask(String code) {
        return taskRepository.findTaskByCode(code);
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void addUserToTask(String username, String taskCode) {
       AppUser user =  appUserRepository.findByUsername(username);
       Task task = taskRepository.findTaskByCode(taskCode);
       task.setUser(user);
    }
}
