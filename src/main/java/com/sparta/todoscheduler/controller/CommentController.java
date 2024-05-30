package com.sparta.todoscheduler.controller;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedulers")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{schedulerId}/comments")
    public CommentResponseDto createComment(@PathVariable Long schedulerId, @RequestBody CommentRequestDto requestDto) {
        requestDto.setSchedulerId(schedulerId);
        return commentService.createComment(requestDto);
    }

    @GetMapping("{schedulerId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long schedulerId) {
        return commentService.getCommentsBySchedulerId(schedulerId);
    }

    @DeleteMapping("{schedulerId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long schedulerId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
