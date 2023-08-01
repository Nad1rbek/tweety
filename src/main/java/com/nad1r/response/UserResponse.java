package com.nad1r.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponse {

    private long id;
    private String name;
    private String email;
    private String username;
    private LocalDate birthday;
    private String bio;
    private String location;
    private String webSite;
    private LocalDateTime creationTimestamp;
    private List<TweetResponse> tweets;
    private List<LikeResponse> likes;
    private String profileImageLink;
}
