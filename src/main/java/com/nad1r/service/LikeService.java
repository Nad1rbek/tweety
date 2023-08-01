package com.nad1r.service;

import com.nad1r.exception.NotFoundException;
import com.nad1r.model.Like;
import com.nad1r.model.Tweet;
import com.nad1r.model.User;
import com.nad1r.repository.LikeRepository;
import com.nad1r.request.LikeCreateRequest;
import com.nad1r.response.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final TweetService tweetService;
    private final ModelMapper modelMapper;

    // return null to disallow the creation of multiple likes
    public LikeResponse create(LikeCreateRequest request) {
        User user = userService.findUserById(request.getUserId());
        Tweet tweet = tweetService.findTweetById(request.getTweetId());
        Like like = Like.builder()
                .tweet(tweet)
                .user(user).build();
        if (!checkUserLikedForThisTweet(request.getUserId(), request.getTweetId()))
            return modelMapper.map(likeRepository.save(like), LikeResponse.class);
        return null;
    }

    public void deleteLikeById(Long id) {
        Like toBeDeletedLike = findLikeById(id);
        likeRepository.delete(toBeDeletedLike);
    }

    protected Like findLikeById(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Like not found"));
    }

    protected boolean checkUserLikedForThisTweet(Long userId, Long tweetId) {
        return likeRepository.findLikeByUser_IdAndTweet_Id(userId, tweetId).isPresent();
    }

    public Page<LikeResponse> getTweetsLikesByTweetId(Long id, Pageable page) {
        Tweet tweet = tweetService.findTweetById(id);
        return likeRepository.findLikesByTweet_Id(tweet.getId(), page)
                .map(x -> modelMapper.map(x, LikeResponse.class));
    }

    public Page<LikeResponse> getLikesByUserId(Long id, Pageable page) {
        User inDB = userService.findUserById(id);
        return likeRepository.findLikesByUser_Id(inDB.getId(), page)
                .map(x -> modelMapper.map(x, LikeResponse.class));
    }
}