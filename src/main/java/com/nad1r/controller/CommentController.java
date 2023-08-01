package com.nad1r.controller;

import com.nad1r.request.CommentCreateRequest;
import com.nad1r.request.UpdateCommentRequest;
import com.nad1r.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    ResponseEntity<?> create(@RequestBody CommentCreateRequest request) {
        return ResponseEntity.status(201).body(commentService.create(request));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getCommentById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(commentService.getCommentById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateCommentById(@PathVariable Long id,
                                                 @RequestBody UpdateCommentRequest request) {
        return ResponseEntity.status(200).body(commentService.updateCommentById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().build();
    }
}