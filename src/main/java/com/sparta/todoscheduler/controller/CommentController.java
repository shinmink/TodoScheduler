package com.sparta.todoscheduler.controller;

import com.sparta.todoscheduler.entity.Comment;
import com.sparta.todoscheduler.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<Comment>> getCommentsByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(commentService.getCommentsByScheduleId(scheduleId));
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.addComment(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestParam String content, @RequestParam String currentUserId) {
        return ResponseEntity.ok(commentService.updateComment(id, content, currentUserId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @RequestParam String currentUserId) {
        commentService.deleteComment(id, currentUserId);
        return ResponseEntity.ok().build();
    }
}
