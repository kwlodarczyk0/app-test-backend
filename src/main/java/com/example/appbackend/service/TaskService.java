package com.example.appbackend.service;

import com.example.appbackend.domain.Task;

public interface TaskService {
    Task getTask(String code);

    Task addTask(Task task);

    void addUserToTask(String username,String taskCode);

}
