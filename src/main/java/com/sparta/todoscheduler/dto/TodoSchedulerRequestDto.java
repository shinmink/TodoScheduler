package com.sparta.todoscheduler.dto;

import lombok.Getter;

import java.sql.Date;

@Getter
public class TodoSchedulerRequestDto {
    private String title;
    private String contents;
    private String username;
    private String password;
    private Date date;
}