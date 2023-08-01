package com.nad1r.request;

import lombok.Getter;

@Getter
public class TweetCreateRequest {
    private String text;
    private Long userId;
}