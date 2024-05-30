package com.sparta.todoscheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TodoSchedulerRequestDto {
    private String title;
    private String contents;
    private LocalDate date;
}
