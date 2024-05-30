package com.sparta.todoscheduler.controller;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.security.UserDetailsImpl;
import com.sparta.todoscheduler.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(requestDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{schedulerId}")
    public List<CommentResponseDto> getComments(@PathVariable Long schedulerId) {
        return commentService.getComments(schedulerId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(id, requestDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(id, userDetails.getUser());
        return ResponseEntity.ok().build();
    }
}