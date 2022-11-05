package com.example.appbackend.controller.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangePassword implements Serializable {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String repeatedNewPassword;
}
