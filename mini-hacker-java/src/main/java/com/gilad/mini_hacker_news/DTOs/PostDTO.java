package com.gilad.mini_hacker_news.DTOs;

import com.gilad.mini_hacker_news.entities.Post;

public class PostDTO {
  private String id;
  private String text;
  private double score;

  public PostDTO(String id, String text, double score) {
    this.id = id;
    this.text = text;
    this.score = score;
  }

  public static PostDTO fromPost(Post post) {
    return new PostDTO(post.getId(), post.getText(), post.getScore());
  }

  public static Post toPost(PostDTO postDTO) {
    Post post = new Post();
    post.setId(postDTO.getId());
    post.setText(postDTO.getText());
    return post;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

}
