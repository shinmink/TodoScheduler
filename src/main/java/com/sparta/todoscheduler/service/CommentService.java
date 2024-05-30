package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.entity.Comment;
import com.sparta.todoscheduler.repository.CommentRepository;
import com.sparta.todoscheduler.repository.TodoSchedulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoSchedulerRepository todoSchedulerRepository;

    public List<Comment> getCommentsByScheduleId(Long scheduleId) {
        if (!todoSchedulerRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("일정이 DB에 존재하지 않습니다.");
        }
        return commentRepository.findByScheduleId(scheduleId);
    }

    public Comment addComment(Comment comment) {
        if (comment.getScheduleId() == null) {
            throw new IllegalArgumentException("일정 ID를 입력하지 않았습니다.");
        }
        if (comment.getContent() == null || comment.getContent().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용이 비어 있습니다.");
        }
        if (!todoSchedulerRepository.existsById(comment.getScheduleId())) {
            throw new IllegalArgumentException("일정이 DB에 존재하지 않습니다.");
        }
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id, String content, String currentUserId) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 DB에 존재하지 않습니다."));
        if (!comment.getUserId().equals(currentUserId)) {
            throw new IllegalArgumentException("선택한 댓글의 사용자가 현재 사용자와 일치하지 않습니다.");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("댓글 내용이 비어 있습니다.");
        }
        comment.setContent(content);
        return comment;
    }

    public void deleteComment(Long id, String currentUserId) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 DB에 존재하지 않습니다."));
        if (!comment.getUserId().equals(currentUserId)) {
            throw new IllegalArgumentException("선택한 댓글의 사용자가 현재 사용자와 일치하지 않습니다.");
        }
        commentRepository.deleteById(id);
    }
}
