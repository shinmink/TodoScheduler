package com.sparta.todoscheduler.repository;

import com.sparta.todoscheduler.entity.TodoScheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoSchedulerRepository extends JpaRepository<TodoScheduler, Long> {
}

