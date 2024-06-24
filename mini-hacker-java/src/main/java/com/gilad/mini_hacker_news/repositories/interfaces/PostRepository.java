package com.gilad.mini_hacker_news.repositories.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gilad.mini_hacker_news.entities.Post;

public interface PostRepository extends MongoRepository<Post, String> {

  // Optional custom methods
  Optional<Post> findByIdAndFetchVotes(String id);

  List<Post> findTopPostsByScore(int limit);

  List<Post> findPostsByNewest(int limit);
}
