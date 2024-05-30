package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.entity.Comment;
import com.sparta.todoscheduler.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setUsername(requestDto.getUsername());
        comment.setSchedulerId(requestDto.getSchedulerId());
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    @Transactional
    public List<CommentResponseDto> getCommentsBySchedulerId(Long schedulerId) {
        List<Comment> commentList = commentRepository.findByTodoSchedulerId(schedulerId);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            responseDtoList.add(new CommentResponseDto(comment));
        }

        return responseDtoList;
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
