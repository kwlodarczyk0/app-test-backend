package com.example.appbackend.controller.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangeTaskStatus implements Serializable {
    private String taskCode;
    private String status;
}
