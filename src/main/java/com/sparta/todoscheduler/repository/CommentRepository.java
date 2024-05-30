package com.sparta.todoscheduler.repository;

import com.sparta.todoscheduler.entity.Comment;
import com.sparta.todoscheduler.entity.TodoScheduler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySchedulerId(Long schedulerId);
}

