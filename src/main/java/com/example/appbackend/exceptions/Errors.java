package com.example.appbackend.exceptions;

public enum Errors {

    DUPLICATE_USER(1, "This user already exists."),
    PROJECT_NOT_FOUND (2,"This project does not exists"),
    USER_NOT_FOUND (3,"This user does not exists"),
    TASK_NOT_FOUND(4,"This task was not found");

    private final int id;
    private final String message;

    Errors(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() { return id; }
    public String getMessage() { return message; }
}
