package com.nad1r.controller;

import com.nad1r.request.LikeCreateRequest;
import com.nad1r.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    ResponseEntity<?> create(@RequestBody LikeCreateRequest request) {
        return ResponseEntity.status(201).body(likeService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLikeById(@PathVariable Long id) {
        likeService.deleteLikeById(id);
        return ResponseEntity.ok().build();
    }
}