package com.gilad.mini_hacker_news.repositories.interfaces;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gilad.mini_hacker_news.entities.Post;
import com.gilad.mini_hacker_news.entities.Vote;

public interface VoteRepository extends MongoRepository<Vote, String> {
  public List<Vote> findByPostId(String postId);

  public void createVote(Post post, int i);
}
