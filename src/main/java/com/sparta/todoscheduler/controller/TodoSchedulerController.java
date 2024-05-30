package com.sparta.todoscheduler.controller;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import com.sparta.todoscheduler.dto.TodoSchedulerResponseDto;
import com.sparta.todoscheduler.entity.User;
import com.sparta.todoscheduler.security.UserDetailsImpl;
import com.sparta.todoscheduler.service.TodoSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedulers")
public class TodoSchedulerController {

    private final TodoSchedulerService todoSchedulerService;

    @PostMapping
    public TodoSchedulerResponseDto createScheduler(@RequestBody TodoSchedulerRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return todoSchedulerService.createScheduler(requestDto, user);
    }

    @GetMapping
    public List<TodoSchedulerResponseDto> getSchedulers() {
        return todoSchedulerService.getSchedulers();
    }

    @PutMapping("/{id}")
    public TodoSchedulerResponseDto updateScheduler(@PathVariable Long id, @RequestBody TodoSchedulerRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return todoSchedulerService.updateScheduler(id, requestDto, user);
    }

    @DeleteMapping("/{id}")
    public void deleteScheduler(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        todoSchedulerService.deleteScheduler(id, user);
    }

    @PostMapping("/{schedulerId}/scheduler-comments")
    public CommentResponseDto addSchedulerComment(@PathVariable Long schedulerId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return todoSchedulerService.addComment(schedulerId, requestDto, user);
    }

    @GetMapping("/{schedulerId}/scheduler-comments")
    public List<CommentResponseDto> getSchedulerComments(@PathVariable Long schedulerId) {
        return todoSchedulerService.getComments(schedulerId);
    }

    @PutMapping("/{schedulerId}/scheduler-comments/{commentId}")
    public CommentResponseDto updateSchedulerComment(@PathVariable Long schedulerId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return todoSchedulerService.updateComment(schedulerId, commentId, requestDto, user);
    }

    @DeleteMapping("/{schedulerId}/scheduler-comments/{commentId}")
    public void deleteSchedulerComment(@PathVariable Long schedulerId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        todoSchedulerService.deleteComment(schedulerId, commentId, user);
    }
}
