package com.nad1r.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponse {
    private long id;
    private String text;
    private long userId;
    private long tweetId;
    private String name;
    private String username;
    private LocalDateTime creationTimestamp;
    private List<LikeResponse> likes;
    private List<RetweetResponse> retweets;
}
