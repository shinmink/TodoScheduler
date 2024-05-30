package com.sparta.todoscheduler.entity;

import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "todoschedulers")
@NoArgsConstructor
public class TodoScheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "todoScheduler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public TodoScheduler(TodoSchedulerRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.date = requestDto.getDate();
        this.user = user;
        this.comments = new ArrayList<>();
    }

    public void update(TodoSchedulerRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.date = requestDto.getDate();
    }

}
