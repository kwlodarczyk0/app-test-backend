package com.example.appbackend.repository;

import com.example.appbackend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Task findTaskByCode(String name);
}
