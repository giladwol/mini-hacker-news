package com.gilad.mini_hacker_news.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.lang.NonNull;

@Document(collection = "votes")
public class Vote {

  @Id
  private String id;

  @Field
  @NonNull
  private int value; // 1 (upvote) or -1 (downvote)

  @CreatedDate
  private LocalDateTime createdAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Cacheable("voteScore")
  public double getScore(Post post) {
    long ageInSeconds = Duration
        .between(post.getCreatedAt().toInstant(ZoneOffset.UTC), this.getCreatedAt().toInstant(ZoneOffset.UTC))
        .getSeconds() + 1; // to avoid division by zero
    return (double) this.getValue() / ageInSeconds;
  }
}
