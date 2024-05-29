package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import com.sparta.todoscheduler.dto.TodoSchedulerResponseDto;
import com.sparta.todoscheduler.entity.TodoScheduler;
import com.sparta.todoscheduler.repository.TodoSchedulerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoSchedulerService {

    private final TodoSchedulerRepository todoSchedulerRepository;

    public TodoSchedulerService(TodoSchedulerRepository todoSchedulerRepository) {
        this.todoSchedulerRepository = todoSchedulerRepository;
    }

    public TodoSchedulerResponseDto createScheduler(TodoSchedulerRequestDto requestDto) {
        TodoScheduler todoScheduler = new TodoScheduler(requestDto);
        TodoScheduler savedScheduler = todoSchedulerRepository.save(todoScheduler);
        return new TodoSchedulerResponseDto(savedScheduler);
    }

    public List<TodoSchedulerResponseDto> getSchedulers() {
        return todoSchedulerRepository.findAll()
                .stream()
                .map(TodoSchedulerResponseDto::new)
                .collect(Collectors.toList());
    }

    public TodoSchedulerResponseDto updateScheduler(Long id, TodoSchedulerRequestDto requestDto) {
        TodoScheduler todoScheduler = todoSchedulerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));

        if (!todoScheduler.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 올바르지 않습니다.");
        }

        todoScheduler.update(requestDto);
        TodoScheduler updatedScheduler = todoSchedulerRepository.save(todoScheduler);

        return new TodoSchedulerResponseDto(updatedScheduler);
    }

    public void deleteScheduler(Long id, String password) {
        TodoScheduler todoScheduler = todoSchedulerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));

        if (!todoScheduler.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 올바르지 않습니다.");
        }

        todoSchedulerRepository.delete(todoScheduler);
    }
}
