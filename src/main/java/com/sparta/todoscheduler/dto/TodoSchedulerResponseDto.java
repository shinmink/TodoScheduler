package com.sparta.todoscheduler.dto;

import com.sparta.todoscheduler.entity.TodoScheduler;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class TodoSchedulerResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private Date date;
    private List<CommentResponseDto> comments;

    public TodoSchedulerResponseDto(TodoScheduler scheduler) {
        this.id = scheduler.getId();
        this.title = scheduler.getTitle();
        this.contents = scheduler.getContents();
        this.username = scheduler.getUsername();
        this.password = scheduler.getPassword();
        this.date = scheduler.getDate();
        this.comments = scheduler.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
