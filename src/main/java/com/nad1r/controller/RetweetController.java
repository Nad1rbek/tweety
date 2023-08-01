package com.nad1r.controller;

import com.nad1r.request.RetweetCreateRequest;
import com.nad1r.request.UpdateRetweetRequest;
import com.nad1r.service.RetweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/retweets")
@RequiredArgsConstructor
public class RetweetController {
    private final RetweetService retweetService;

    @PostMapping
    ResponseEntity<?> create(@RequestBody RetweetCreateRequest request) {
        return ResponseEntity.status(201).body(retweetService.create(request));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getRetweetById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(retweetService.getRetweetById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateRetweetById(@PathVariable Long id,
                                                 @RequestBody UpdateRetweetRequest request) {
        return ResponseEntity.status(200).body(retweetService.updateRetweetById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRetweetById(@PathVariable Long id) {
        retweetService.deleteRetweetById(id);
        return ResponseEntity.ok().build();
    }
}