package com.example.appbackend.repository;

import com.example.appbackend.model.Comment;
import com.example.appbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Task findTaskByCode(String name);

    @Query(value = "SELECT comments FROM Task WHERE code = ?1 ")
    List<Comment> findComments(String taskCode);

}
