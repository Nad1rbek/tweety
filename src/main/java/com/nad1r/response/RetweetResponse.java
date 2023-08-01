package com.nad1r.response;

import lombok.Data;

@Data
public class RetweetResponse {

    private long id;
    private String text;
    private long userId;
    private long tweetId;
}
