package com.sparta.todoscheduler.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduler_id", nullable = false)
    private TodoScheduler todoScheduler;

    private LocalDateTime createdAt;

    public Comment(String content, String username, TodoScheduler todoScheduler) {
        this.content = content;
        this.username = username;
        this.todoScheduler = todoScheduler;
        this.createdAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void setSchedulerId(Long id) {
        this.todoScheduler.setId(id);
    }
}