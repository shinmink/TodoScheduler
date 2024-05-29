package com.sparta.todoscheduler.entity;

import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class TodoScheduler {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private Date date;

    public TodoScheduler(TodoSchedulerRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.date = requestDto.getDate();



    }

    public void update(TodoSchedulerRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.date = requestDto.getDate();

    }
}