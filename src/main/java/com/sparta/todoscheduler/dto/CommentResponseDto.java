package com.sparta.todoscheduler.dto;

import com.sparta.todoscheduler.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponseDto {
    private Long id;
    private String contents;
    private String username;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContent();
        this.username = comment.getUserId().toString();
    }
}
