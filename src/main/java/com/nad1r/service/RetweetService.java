package com.nad1r.service;

import com.nad1r.exception.NotFoundException;
import com.nad1r.model.Retweet;
import com.nad1r.model.Tweet;
import com.nad1r.model.User;
import com.nad1r.repository.RetweetRepository;
import com.nad1r.request.RetweetCreateRequest;
import com.nad1r.request.UpdateRetweetRequest;
import com.nad1r.response.RetweetResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetweetService {
    private final RetweetRepository retweetRepository;
    private final UserService userService;
    private final TweetService tweetService;
    private final ModelMapper modelMapper;


    public RetweetResponse create(RetweetCreateRequest request){
        User user = userService.findUserById(request.getUserId());
        Tweet tweet = tweetService.findTweetById(request.getTweetId());
        Retweet retweet = Retweet.builder()
                .text(request.getText())
                .user(user)
                .tweet(tweet).build();
        return modelMapper.map(retweetRepository.save(retweet), RetweetResponse.class);
    }

    public RetweetResponse getRetweetById(long id){
        return modelMapper.map(findRetweetById(id), RetweetResponse.class);
    }

    public RetweetResponse updateRetweetById(long id, UpdateRetweetRequest request){
        Retweet retweet = findRetweetById(id);
        retweet.setText(retweet.getText());
        return modelMapper.map(retweetRepository.save(retweet), RetweetResponse.class);
    }

    public void deleteRetweetById(long id){
        Retweet retweet = findRetweetById(id);
        retweetRepository.delete(retweet);
    }

    public Page<RetweetResponse> getRetweetsByTweetId(long id, Pageable page){
        Tweet tweet = tweetService.findTweetById(id);
        return retweetRepository.findRetweetsByTweet_Id(tweet.getId(), page)
                .map(x -> modelMapper.map(x, RetweetResponse.class));
    }
    protected Retweet findRetweetById(long id) {
        return retweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Retweet not found!"));
    }
}
