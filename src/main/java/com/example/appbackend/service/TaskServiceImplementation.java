package com.example.appbackend.service;

import com.example.appbackend.model.AppUser;
import com.example.appbackend.model.Comment;
import com.example.appbackend.model.Project;
import com.example.appbackend.model.Task;
import com.example.appbackend.repository.AppUserRepository;
import com.example.appbackend.repository.CommentRepository;
import com.example.appbackend.repository.ProjectRepository;
import com.example.appbackend.repository.TaskRepository;
import com.example.appbackend.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImplementation implements TaskService {
    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectRepository projectRepository;

    private final CommentRepository commentRepository;

    @Override
    public Task getTask(String code) {
        return taskRepository.findTaskByCode(code);
    }

    @Override
    public Task addTask(Task task,String projectName) {
        Project project = projectRepository.findByName(projectName);
        task.setStatus("OPEN");
        task.setCode(projectName+"-"+(project.getTasks().toArray().length+1));
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

    @Override
    public Task addCommentToTask(String text, String taskCode, String username) {
        Task task = taskRepository.findTaskByCode(taskCode);
        AppUser user = appUserRepository.findByUsername(username);

        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setDate(new Date());
        commentRepository.save(comment);

        task.getComments().add(comment);
        return task;
    }
    @Override
    public Task deleteComment(String taskCode,Long id) throws ApplicationContextException {
        Task task = taskRepository.findTaskByCode(taskCode);
        Comment comment = commentRepository.findOne(id);

        if(comment == null){
            throw new ApplicationContextException("There is no such comment");
        }

        List<Comment> com = new ArrayList<>();

        for (Comment c: task.getComments()) {
            if(!c.getId().equals(comment.getId())) {
                com.add(c);
            }
        }

        task.getComments().clear();
        task.getComments().addAll(com);
        commentRepository.delete(comment);

        return task;

    }




}
