package com.nad1r.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TweetResponse {
    private Long id;
    private String text;
    private Long userId;
    private String name;
    private String username;
    private String userProfileImageLink;
    private LocalDateTime creationTimestamp;
    private List<LikeResponse> likes;
    private List<CommentResponse> comments;
    private List<RetweetResponse> retweets;
}
