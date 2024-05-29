package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.entity.Comment;
import com.sparta.todoscheduler.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setUsername(requestDto.getUsername());
        comment.setSchedulerId(requestDto.getSchedulerId());
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    public List<CommentResponseDto> getCommentsBySchedulerId(Long schedulerId) {
        return commentRepository.findBySchedulerId(schedulerId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
