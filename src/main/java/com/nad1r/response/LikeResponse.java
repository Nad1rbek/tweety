package com.nad1r.response;

import lombok.Data;

@Data
public class LikeResponse {

    private long id;
    private long userId;
    private long tweetId;
}
