package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.dto.CommentRequestDto;
import com.sparta.todoscheduler.dto.CommentResponseDto;
import com.sparta.todoscheduler.dto.TodoSchedulerRequestDto;
import com.sparta.todoscheduler.dto.TodoSchedulerResponseDto;
import com.sparta.todoscheduler.entity.Comment;
import com.sparta.todoscheduler.entity.TodoScheduler;
import com.sparta.todoscheduler.entity.User;
import com.sparta.todoscheduler.entity.UserRoleEnum;
import com.sparta.todoscheduler.repository.CommentRepository;
import com.sparta.todoscheduler.repository.TodoSchedulerRepository;
import com.sparta.todoscheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoSchedulerService {

    private final TodoSchedulerRepository todoSchedulerRepository;
    private final CommentRepository commentRepository;


    public TodoSchedulerResponseDto createScheduler(TodoSchedulerRequestDto requestDto, User user) {
        TodoScheduler todoScheduler = new TodoScheduler(requestDto, user);
        TodoScheduler savedScheduler = todoSchedulerRepository.save(todoScheduler);
        return new TodoSchedulerResponseDto(savedScheduler);
    }

    @Transactional(readOnly = true)
    public List<TodoSchedulerResponseDto> getSchedulers() {
        return todoSchedulerRepository.findAll()
                .stream()
                .map(scheduler -> {
                    List<CommentResponseDto> comments = commentRepository.findByTodoSchedulerId(scheduler.getId())
                            .stream()
                            .map(CommentResponseDto::new)
                            .collect(Collectors.toList());
                    return new TodoSchedulerResponseDto(scheduler, comments);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoSchedulerResponseDto updateScheduler(Long id, TodoSchedulerRequestDto requestDto, User user) {
        TodoScheduler todoScheduler = todoSchedulerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));

        if (!todoScheduler.getUser().equals(user) && user.getRole() != UserRoleEnum.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        todoScheduler.update(requestDto);
        TodoScheduler updatedScheduler = todoSchedulerRepository.save(todoScheduler);

        return new TodoSchedulerResponseDto(updatedScheduler);
    }

    public void deleteScheduler(Long id, User user) {
        TodoScheduler todoScheduler = todoSchedulerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));

        if (!todoScheduler.getUser().equals(user) && user.getRole() != UserRoleEnum.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }

        todoSchedulerRepository.delete(todoScheduler);
    }

    @Transactional
    public CommentResponseDto addComment(Long schedulerId, CommentRequestDto requestDto, User user) {
        TodoScheduler todoScheduler = todoSchedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정은 존재하지 않습니다."));
        Comment comment = new Comment(requestDto.getContent(), user.getUsername(), todoScheduler);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long schedulerId) {
        return commentRepository.findByTodoSchedulerId(schedulerId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long schedulerId, Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));

        if (!comment.getTodoScheduler().getId().equals(schedulerId)) {
            throw new IllegalArgumentException("해당 일정에 댓글이 없습니다.");
        }

        if (!comment.getUsername().equals(user.getUsername()) && user.getRole() != UserRoleEnum.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        comment.updateContent(requestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long schedulerId, Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));

        if (!comment.getTodoScheduler().getId().equals(schedulerId)) {
            throw new IllegalArgumentException("해당 일정에 댓글이 없습니다.");
        }

        if (!comment.getUsername().equals(user.getUsername()) && user.getRole() != UserRoleEnum.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
