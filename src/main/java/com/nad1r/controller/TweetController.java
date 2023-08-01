package com.nad1r.controller;

import com.nad1r.request.TweetCreateRequest;
import com.nad1r.request.UpdateTweetRequest;
import com.nad1r.response.TweetResponse;
import com.nad1r.service.CommentService;
import com.nad1r.service.LikeService;
import com.nad1r.service.RetweetService;
import com.nad1r.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tweets")
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;
    private final LikeService likeService;
    private final RetweetService retweetService;
    private final CommentService commentService;

    @PostMapping
    ResponseEntity<TweetResponse> create(@RequestBody TweetCreateRequest request) {
        TweetResponse tweetResponse = tweetService.create(request);
        System.out.println(tweetResponse.toString());
        return ResponseEntity.status(201).body(tweetResponse);
    }

    @GetMapping
    ResponseEntity<Page<?>> getTweets(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.status(200).body(tweetService.getTweets(page));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getTweetById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(tweetService.getTweetById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTweetById(@PathVariable Long id,
                                             @RequestBody UpdateTweetRequest request) {
        return ResponseEntity.status(200).body(tweetService.updateTweetById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweetById(@PathVariable Long id) {
        tweetService.deleteTweetById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/likes")
    ResponseEntity<Page<?>> getTweetsLikesByTweetId(@PathVariable Long id,
                                                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.status(200).body(likeService.getTweetsLikesByTweetId(id, page));
    }

    @GetMapping("/{id}/comments")
    ResponseEntity<Page<?>> getTweetsCommentsByTweetId(@PathVariable Long id,
                                                                @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.status(200).body(commentService.getCommentsByTweetId(id, page));
    }

    @GetMapping("/{id}/retweets")
    ResponseEntity<Page<?>> getTweetsRetweetsByTweetId(@PathVariable Long id,
                                                                @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.status(200).body(retweetService.getRetweetsByTweetId(id, page));
    }
}