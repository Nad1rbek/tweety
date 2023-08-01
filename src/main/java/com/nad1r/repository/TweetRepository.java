package com.nad1r.repository;

import com.nad1r.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Page<Tweet> findAllByUser_Id(Long id, Pageable page);
}
