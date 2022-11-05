package com.example.appbackend.controller;

import com.example.appbackend.controller.dto.ChangeTaskStatus;
import com.example.appbackend.controller.dto.CommentToTask;
import com.example.appbackend.controller.dto.DeleteComment;
import com.example.appbackend.model.Task;
import com.example.appbackend.service.interfaces.ProjectService;
import com.example.appbackend.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

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
        taskService.addTask(task,projectName);
        //projectService.addTaskToProject(task.getCode(), projectName);
        return task;
    }

    @PostMapping("/task/addUser/{taskCode}")
    public Task addUserToTask(@PathVariable String taskCode,@RequestBody String username){
        return taskService.addUserToTask(username,taskCode);
    }

    @PostMapping("/task/addComment/{taskCode}")
    public Task addCommentToTask(@PathVariable String taskCode, @RequestBody CommentToTask commentToTask){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return taskService.addCommentToTask(commentToTask.getText(),taskCode,auth.getPrincipal().toString());
    }

    @DeleteMapping("/task/removeComment/{taskCode}")
    public Task removeCommentFromTask(@PathVariable String taskCode, @RequestBody DeleteComment deleteComment){
        return taskService.deleteComment(taskCode,deleteComment.getId());
    }


    //define a location -- change IT - for each project create the new directory where will be stored attachments
    public static final String DIRECTORY = System.getProperty("user.dir")+"/uploads";

    //define a method to upload files
    @PostMapping("/task/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles) throws IOException {
        List<String> filenames = new ArrayList<>();
        for(MultipartFile file: multipartFiles){
            String filename =  org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY,filename).toAbsolutePath().normalize();
            copy(file.getInputStream(),fileStorage,REPLACE_EXISTING);
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }


    //define a method to download file
    @GetMapping("/task/download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if(!Files.exists(filePath)){
            throw new FileNotFoundException(filename+"was not found on the server");
        }

        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name",filename);
        httpHeaders.add(CONTENT_DISPOSITION,"attachment;File-Name="+resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }




}
