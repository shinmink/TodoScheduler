package com.sparta.todoscheduler.dto;

import com.sparta.todoscheduler.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String username;
    private Long schedulerId;
    private Timestamp createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUsername();
        this.schedulerId = comment.getSchedulerId();
        this.createdAt = comment.getCreatedAt();
    }
}
