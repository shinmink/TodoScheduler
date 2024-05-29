package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import com.sparta.todoscheduler.dto.TodoSchedulerResponseDto;
import com.sparta.todoscheduler.entity.TodoScheduler;
import com.sparta.todoscheduler.repository.TodoSchedulerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class TodoSchedulerService {

    private final TodoSchedulerRepository todoSchedulerRepository;

    public TodoSchedulerService(TodoSchedulerRepository todoSchedulerRepository) {
        this.todoSchedulerRepository = todoSchedulerRepository;
    }

    public TodoSchedulerResponseDto createScheduler(TodoSchedulerRequestDto requestDto) {
        TodoScheduler todoscheduler = new TodoScheduler(requestDto);
        Long id = todoSchedulerRepository.save(todoscheduler);
        todoscheduler.setId(id);
        return new TodoSchedulerResponseDto(todoscheduler);
    }

    public List<TodoSchedulerResponseDto> getSchedulers() {
        return todoSchedulerRepository.findAll();
    }

    public Long updateScheduler(Long id, TodoSchedulerRequestDto requestDto) {
        TodoScheduler todoscheduler = todoSchedulerRepository.findById(id);
        if (todoscheduler != null) {
            if (!todoscheduler.getPassword().equals(requestDto.getPassword())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 올바르지 않습니다.");
            }
            validateRequestDto(requestDto);
            todoSchedulerRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    public Long deleteScheduler(Long id, TodoSchedulerRequestDto requestDto) {
        TodoScheduler todoscheduler = todoSchedulerRepository.findById(id);
        if (todoscheduler != null) {
            if (!todoscheduler.getPassword().equals(requestDto.getPassword())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 올바르지 않습니다.");
            }
            todoSchedulerRepository.delete(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    private void validateRequestDto(TodoSchedulerRequestDto requestDto) {
        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (requestDto.getContents() == null || requestDto.getContents().isEmpty()) {
            throw new IllegalArgumentException("Contents cannot be null or empty");
        }
        if (requestDto.getUsername() == null || requestDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (requestDto.getDate() == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }
}
