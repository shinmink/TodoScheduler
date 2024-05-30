package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import com.sparta.todoscheduler.dto.TodoSchedulerResponseDto;
import com.sparta.todoscheduler.entity.TodoScheduler;
import com.sparta.todoscheduler.entity.User;
import com.sparta.todoscheduler.entity.UserRoleEnum;
import com.sparta.todoscheduler.repository.TodoSchedulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class TodoSchedulerService {
    @Autowired
    private TodoSchedulerRepository todoSchedulerRepository;

    public void createScheduler(TodoSchedulerRequestDto requestDto, User user) {
        TodoScheduler scheduler = new TodoScheduler();
        scheduler.setTitle(requestDto.getTitle());
        scheduler.setContents(requestDto.getContents());
        scheduler.setDate(requestDto.getDate());
        scheduler.setUser(user);
        todoSchedulerRepository.save(scheduler);
    }

    public List<TodoSchedulerResponseDto> getSchedulers() {
        List<TodoScheduler> schedulers = todoSchedulerRepository.findAll();
        return schedulers.stream()
                .map(TodoSchedulerResponseDto::new)
                .collect(Collectors.toList());
    }

    public void updateScheduler(Long id, TodoSchedulerRequestDto requestDto, User user) {
        TodoScheduler scheduler = todoSchedulerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid scheduler Id:" + id));
        if (scheduler.getUser().equals(user) || Objects.equals(user.getRole(), UserRoleEnum.ADMIN.toString())) {
            scheduler.setTitle(requestDto.getTitle());
            scheduler.setContents(requestDto.getContents());
            scheduler.setDate(requestDto.getDate());
            todoSchedulerRepository.save(scheduler);
        } else {
            throw new AccessDeniedException("You do not have permission to update this scheduler");
        }
    }

    public void deleteScheduler(Long id, User user) {
        TodoScheduler scheduler = todoSchedulerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid scheduler Id:" + id));
        if (scheduler.getUser().equals(user) || Objects.equals(user.getRole(), UserRoleEnum.ADMIN.toString())) {
            todoSchedulerRepository.delete(scheduler);
        } else {
            throw new AccessDeniedException("You do not have permission to delete this scheduler");
        }
    }
}
