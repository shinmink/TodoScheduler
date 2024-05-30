package com.sparta.todoscheduler.dto;

import com.sparta.todoscheduler.entity.TodoScheduler;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
public class TodoSchedulerResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private Date date;
    private List<CommentResponseDto> comments;

    public TodoSchedulerResponseDto(TodoScheduler todoScheduler, List<CommentResponseDto> comments) {
        this.id = todoScheduler.getId();
        this.title = todoScheduler.getTitle();
        this.contents = todoScheduler.getContents();
        this.username = todoScheduler.getUser().getUsername();
        this.date = todoScheduler.getDate();
        this.comments = comments;
    }

    public TodoSchedulerResponseDto(TodoScheduler todoScheduler) {
        this.id = todoScheduler.getId();
        this.title = todoScheduler.getTitle();
        this.contents = todoScheduler.getContents();
        this.username = todoScheduler.getUser().getUsername();
        this.date = todoScheduler.getDate();
    }
}