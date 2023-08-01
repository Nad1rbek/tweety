package com.nad1r.service;

import com.nad1r.exception.NotFoundException;
import com.nad1r.model.Tweet;
import com.nad1r.model.User;
import com.nad1r.repository.TweetRepository;
import com.nad1r.request.TweetCreateRequest;
import com.nad1r.request.UpdateTweetRequest;
import com.nad1r.response.TweetResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TweetResponse create(TweetCreateRequest request){
        User user = userService.findUserById(request.getUserId());
        Tweet tweet = Tweet.builder()
                .text(request.getText())
                .user(user).build();
        return modelMapper.map(tweetRepository.save(tweet), TweetResponse.class);
    }

    public Page<TweetResponse> getTweets(Pageable page){
        return tweetRepository.findAll(page).map(x -> modelMapper.map(x, TweetResponse.class));
    }

    public TweetResponse getTweetById(long id){
        return modelMapper.map(findTweetById(id), TweetResponse.class);
    }

    public TweetResponse updateTweetById(long id, UpdateTweetRequest request){
        Tweet tweet = findTweetById(id);
        tweet.setText(request.getText());
        return modelMapper.map(tweetRepository.save(tweet), TweetResponse.class);
    }

    public void deleteTweetById(long id){
        Tweet tweet = findTweetById(id);
        tweetRepository.delete(tweet);
    }

    public  Page<?> getTweetsByUserId(long id, Pageable page){
        User user = userService.findUserById(id);
        return tweetRepository.findAllByUser_Id(user.getId(), page)
                .map(x -> modelMapper.map(x, TweetResponse.class));
    }
    protected Tweet findTweetById(long id){
        return tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found!"));
    }
}
