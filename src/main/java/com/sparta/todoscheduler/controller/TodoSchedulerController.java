package com.sparta.todoscheduler.controller;

import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import com.sparta.todoscheduler.dto.TodoSchedulerResponseDto;
import com.sparta.todoscheduler.service.TodoSchedulerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedulers")
public class TodoSchedulerController {

    private final TodoSchedulerService todoSchedulerService;

    public TodoSchedulerController(TodoSchedulerService todoSchedulerService) {
        this.todoSchedulerService = todoSchedulerService;
    }

    @PostMapping
    public TodoSchedulerResponseDto createScheduler(@RequestBody TodoSchedulerRequestDto requestDto) {
        return todoSchedulerService.createScheduler(requestDto);
    }

    @GetMapping
    public List<TodoSchedulerResponseDto> getSchedulers() {
        return todoSchedulerService.getSchedulers();
    }

    @PutMapping("/{id}")
    public TodoSchedulerResponseDto updateScheduler(@PathVariable Long id, @RequestBody TodoSchedulerRequestDto requestDto) {
        return todoSchedulerService.updateScheduler(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteScheduler(@PathVariable Long id, @RequestBody String password) {
        todoSchedulerService.deleteScheduler(id, password);
    }
}
