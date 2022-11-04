package com.example.appbackend.service.interfaces;

import com.example.appbackend.model.Task;

public interface TaskService {
    Task getTask(String code);
    Task addTask(Task task,String projectName);
    Task addUserToTask(String username,String taskCode);
    Task changeTaskStatus(String code, String status);

}
