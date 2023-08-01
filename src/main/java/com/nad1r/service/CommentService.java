package com.nad1r.service;

import com.nad1r.exception.NotFoundException;
import com.nad1r.model.Comment;
import com.nad1r.model.Tweet;
import com.nad1r.model.User;
import com.nad1r.repository.CommentRepository;
import com.nad1r.request.CommentCreateRequest;
import com.nad1r.request.UpdateCommentRequest;
import com.nad1r.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TweetService tweetService;
    private final ModelMapper modelMapper;

    public CommentResponse create(CommentCreateRequest request) {
        User user = userService.findUserById(request.getUserId());
        Tweet tweet = tweetService.findTweetById(request.getTweetId());
        Comment comment = Comment.builder()
                .text(request.getText())
                .user(user)
                .tweet(tweet).build();
        return modelMapper.map(commentRepository.save(comment), CommentResponse.class);
    }

    public CommentResponse getCommentById(Long id) {
        return modelMapper.map(findCommentById(id), CommentResponse.class);
    }

    public CommentResponse updateCommentById(Long id, UpdateCommentRequest request) {
        Comment comment = findCommentById(id);
        comment.setText(request.getText());
        return modelMapper.map(commentRepository.save(comment), CommentResponse.class);
    }

    public void deleteCommentById(Long id) {
        Comment inDB = findCommentById(id);
        commentRepository.delete(inDB);
    }

    protected Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found!"));
    }

    public Page<CommentResponse> getCommentsByTweetId(Long id, Pageable page) {
        Tweet tweet = tweetService.findTweetById(id);
        return commentRepository.findCommentsByTweet_Id(tweet.getId(), page)
                .map(x -> modelMapper.map(x, CommentResponse.class));
    }
}