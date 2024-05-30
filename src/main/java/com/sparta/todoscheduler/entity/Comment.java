package com.sparta.todoscheduler.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이디 (고유번호)

    @Column(nullable = false)
    private String content; // 댓글 내용

    @Column(nullable = false)
    private String userId; // 사용자 아이디

    @Column(nullable = false)
    private Long scheduleId; // 일정 아이디

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 작성일자
}