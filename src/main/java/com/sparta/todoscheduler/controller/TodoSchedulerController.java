package com.sparta.todoscheduler.controller;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import com.sparta.todoscheduler.dto.TodoSchedulerResponseDto;
import com.sparta.todoscheduler.entity.User;
import com.sparta.todoscheduler.security.UserDetailsImpl;
import com.sparta.todoscheduler.service.TodoSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedulers")
public class TodoSchedulerController {
    @Autowired
    private TodoSchedulerService todoSchedulerService;

    @PostMapping
    public ResponseEntity<?> createScheduler(@RequestBody TodoSchedulerRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoSchedulerService.createScheduler(requestDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<TodoSchedulerResponseDto> getSchedulers() {
        return todoSchedulerService.getSchedulers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateScheduler(@PathVariable Long id, @RequestBody TodoSchedulerRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoSchedulerService.updateScheduler(id, requestDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScheduler(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoSchedulerService.deleteScheduler(id, userDetails.getUser());
        return ResponseEntity.ok().build();
    }
}
