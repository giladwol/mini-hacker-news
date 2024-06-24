package com.gilad.mini_hacker_news.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gilad.mini_hacker_news.DTOs.PostDTO;
import com.gilad.mini_hacker_news.entities.Post;
import com.gilad.mini_hacker_news.repositories.interfaces.PostRepository;
import com.gilad.mini_hacker_news.repositories.interfaces.VoteRepository;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private VoteRepository voteRepository;

  // #region Post CRUD operations

  // Endpoint to retrieve all posts
  @GetMapping
  public ResponseEntity<List<PostDTO>> getPosts() {
    List<Post> posts = postRepository.findAll();
    return ResponseEntity.ok(posts.stream().map(PostDTO::fromPost).toList());
  }

  // Endpoint to create a new post
  @PostMapping
  public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto) {
    if (postDto.getText().isEmpty()) {
      return ResponseEntity.badRequest().build(); // Handle empty post text
    }
    Post post = PostDTO.toPost(postDto);
    post.setCreatedAt(LocalDateTime.now());
    Post savedPost = postRepository.save(post);
    return ResponseEntity.ok(PostDTO.fromPost(savedPost));
  }

  // Endpoint to update a post's text
  @PutMapping("/{id}")
  public ResponseEntity<PostDTO> updatePost(@PathVariable String id, @RequestBody PostDTO updatedPost) {
    if (updatedPost.getText().isEmpty()) {
      return ResponseEntity.badRequest().build(); // Handle empty post text
    }
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      return ResponseEntity.notFound().build(); // Handle non-existent post
    }
    Post existingPost = optionalPost.get();
    existingPost.setText(updatedPost.getText());
    Post savedPost = postRepository.save(existingPost);
    return ResponseEntity.ok(PostDTO.fromPost(savedPost));
  }

  // Endpoint to delete a post
  @PostMapping("/{id}/delete")
  public ResponseEntity<Void> deletePost(@PathVariable String id) {
    if (id.isEmpty()) {
      return ResponseEntity.badRequest().build(); // Handle empty post ID
    }
    postRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  // #endregion

  // #region Vote operations

  // Endpoint to upvote a post
  @PostMapping("/{id}/upvote")
  public ResponseEntity<PostDTO> upvotePost(@PathVariable String id) {
    if (id.isEmpty()) {

      return ResponseEntity.badRequest().build(); // Handle empty post ID
    }

    Optional<Post> optionalPost = postRepository.findByIdAndFetchVotes(id);
    if (optionalPost.isEmpty()) {
      return ResponseEntity.notFound().build(); // Handle non-existent post
    }
    Post post = optionalPost.get();
    voteRepository.createVote(post, 1); // Create a new vote with value 1 (upvote)
    postRepository.save(post);
    return ResponseEntity.ok(PostDTO.fromPost(post));
  }

  // Endpoint to downvote a post (similar logic to upvote)
  @PostMapping("/{id}/downvote")
  public ResponseEntity<PostDTO> downvotePost(@PathVariable String id) {
    if (id.isEmpty()) {
      return ResponseEntity.badRequest().build(); // Handle empty post ID
    }

    Optional<Post> optionalPost = postRepository.findByIdAndFetchVotes(id);
    if (optionalPost.isEmpty()) {
      return ResponseEntity.notFound().build(); // Handle non-existent post
    }
    Post post = optionalPost.get();
    voteRepository.createVote(post, -1); // Create a new vote with value -1 (downvote)
    postRepository.save(post);
    return ResponseEntity.ok(PostDTO.fromPost(post));
  }

  // #endregion

  // Endpoint to retrieve top posts
  @GetMapping("/top")
  public ResponseEntity<List<PostDTO>> getTopPosts(@RequestParam(defaultValue = "50") int limit) {
    List<Post> topPosts = postRepository.findTopPostsByScore(limit);
    return ResponseEntity.ok(topPosts.stream().map(PostDTO::fromPost).toList());
  }

  @GetMapping("/new")
  public ResponseEntity<List<PostDTO>> getNewPosts(@RequestParam(defaultValue = "50") int limit) {
    List<Post> newPosts = postRepository.findPostsByNewest(limit);
    return ResponseEntity.ok(newPosts.stream().map(PostDTO::fromPost).toList());
  }
}
