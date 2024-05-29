package com.sparta.todoscheduler.controller;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedulers/{schedulerId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentResponseDto createComment(@PathVariable Long schedulerId, @RequestBody CommentRequestDto requestDto) {
        requestDto.setSchedulerId(schedulerId);
        return commentService.createComment(requestDto);
    }

    @GetMapping
    public List<CommentResponseDto> getComments(@PathVariable Long schedulerId) {
        return commentService.getCommentsBySchedulerId(schedulerId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long schedulerId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
