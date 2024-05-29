package com.sparta.todoscheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class TodoSchedulerRequestDto {
    private String title;
    private String contents;
    private String username;
    private String password;
    private Date date;
}
