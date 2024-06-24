package com.gilad.mini_hacker_news.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.lang.NonNull;

@Document(collection = "posts")
public class Post {

  @Id
  private String id;

  @Field
  @NonNull
  private String text;

  @DBRef
  private List<Vote> votes = new ArrayList<>();

  @CreatedDate
  private LocalDateTime createdAt;

  private double score;

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

  public List<Vote> getVotes() {
    return votes;
  }

  public void setVotes(List<Vote> votes) {
    this.votes = votes;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public double getScore() {
    return score;
  }

  private static Map<String, Double> scoreCache = new HashMap<>();

  public void updateScore() {
    updateScore(false);
  }

  /**
   * Updates the score of the post.
   * If the score is already cached and the 'force' flag is false, the cached
   * score is used.
   * Otherwise, the score is calculated by summing the value of each vote divided
   * by the age of the vote in seconds.
   * The calculated score is then stored in the cache.
   *
   * @param force a boolean flag indicating whether to force the score update or
   *              use the cached score
   */
  public void updateScore(boolean force) {
    if (!force && scoreCache.containsKey(this.getId())) {
      this.score = scoreCache.get(this.getId());

      System.out.println("Post id: " + this.getId() + ", Score (from cache): " + this.score);
    } else {
      this.score = this.getVotes().stream().mapToDouble(vote -> {
        return vote.getScore(this);
      }).sum();
      scoreCache.put(this.getId(), this.score);

      System.out.println("Post id: " + this.getId() + ", Score: " + this.score);
    }
  }
}
