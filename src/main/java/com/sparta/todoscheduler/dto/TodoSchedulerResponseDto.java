package com.sparta.todoscheduler.dto;

import com.sparta.todoscheduler.entity.TodoScheduler;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Setter
@Getter
public class TodoSchedulerResponseDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDate date;
    private String username;

    public TodoSchedulerResponseDto(TodoScheduler scheduler) {
        this.id = scheduler.getId();
        this.title = scheduler.getTitle();
        this.contents = scheduler.getContents();
        this.date = scheduler.getDate();
        this.username = scheduler.getUser().getUsername();
    }
}