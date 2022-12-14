package com.example.appbackend.service;


import com.example.appbackend.repository.CommentRepository;
import com.example.appbackend.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImplementation implements CommentService {

    private final CommentRepository commentRepository;


}
