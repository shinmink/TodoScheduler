package com.sparta.todoscheduler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "댓글 내용이 비어 있습니다.")
    @Column(nullable = false)
    private String content; // 댓글 내용

    @NotBlank(message = "사용자 아이디가 비어 있습니다.")
    @Column(nullable = false)
    private Long userId; // 사용자 아이디

    @NotNull(message = "일정 ID를 입력하지 않았습니다.")
    @Column(nullable = false)
    private Long scheduleId; // 일정 아이디

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 작성일자
}