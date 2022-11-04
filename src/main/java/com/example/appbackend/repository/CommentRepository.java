package com.example.appbackend.repository;

import com.example.appbackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query(value = "SELECT c FROM Comment c WHERE c.id = ?1 ")
    Comment findOne(Long id);

    @Query(value = "SELECT comments FROM Task WHERE code = ?1 ")
    List<Comment> findComments(String taskCode);

}
