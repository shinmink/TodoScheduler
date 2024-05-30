package com.sparta.todoscheduler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이디 (고유번호)

    @Column(nullable = false, unique = true)
    private String nickname; // 별명

    @Column(nullable = false, unique = true)
    private String username; // 사용자이름

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String role; // 권한 (일반, 어드민)

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성일자

}