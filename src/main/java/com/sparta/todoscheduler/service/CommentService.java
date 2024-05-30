package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.entity.Comment;
import com.sparta.todoscheduler.entity.TodoScheduler;
import com.sparta.todoscheduler.entity.User;
import com.sparta.todoscheduler.entity.UserRoleEnum;
import com.sparta.todoscheduler.repository.CommentRepository;
import com.sparta.todoscheduler.repository.TodoSchedulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TodoSchedulerRepository todoSchedulerRepository;

    public void createComment(CommentRequestDto requestDto, User user) {
        TodoScheduler scheduler = todoSchedulerRepository.findById(requestDto.getSchedulerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid scheduler Id:" + requestDto.getSchedulerId()));
        Comment comment = new Comment();
        comment.setContents(requestDto.getContents());
        comment.setUser(user);
        comment.setScheduler(scheduler);
        commentRepository.save(comment);
    }

    public List<CommentResponseDto> getComments(Long schedulerId) {
        List<Comment> comments = commentRepository.findBySchedulerId(schedulerId);
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public void updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment Id:" + id));
        if (comment.getUser().equals(user) || user.getRole() == UserRoleEnum.ADMIN) {
            comment.setContents(requestDto.getContents());
            commentRepository.save(comment);
        } else {
            throw new AccessDeniedException("You do not have permission to update this comment");
        }
    }

    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment Id:" + id));
        if (comment.getUser().equals(user) || user.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.delete(comment);
        } else {
            throw new AccessDeniedException("You do not have permission to delete this comment");
        }
    }
}
