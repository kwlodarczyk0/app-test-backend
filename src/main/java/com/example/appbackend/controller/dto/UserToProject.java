package com.example.appbackend.controller.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserToProject implements Serializable {
    private String username;
    private String projectName;
}
