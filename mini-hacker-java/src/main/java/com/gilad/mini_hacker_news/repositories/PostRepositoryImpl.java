package com.gilad.mini_hacker_news.repositories;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.gilad.mini_hacker_news.entities.Post;
import com.gilad.mini_hacker_news.repositories.interfaces.PostRepository;

@Repository
public class PostRepositoryImpl implements PostRepository {

  private final MongoTemplate mongoTemplate;

  public PostRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public <S extends Post> S save(S entity) {
    mongoTemplate.save(entity);
    return entity;
  }

  @Override
  public Optional<Post> findById(String id) {
    return Optional.ofNullable(mongoTemplate.findById(id, Post.class));
  }

  @Override
  public void deleteById(String id) {
    mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Post.class);
  }

  // Optional custom method implementation
  @Override
  public Optional<Post> findByIdAndFetchVotes(String id) {
    Query query = Query.query(Criteria.where("id").is(id));
    Post post = mongoTemplate.findOne(query, Post.class);
    if (post == null) {
      System.out.println("No post found with id: " + id);
    } else if (post.getVotes() == null) {
      System.out.println("Post found, but votes field is null");
    } else {
      System.out.println("Post found, votes: " + post.getVotes());
    }
    return Optional.ofNullable(post);
  }

  @Override
  public List<Post> findTopPostsByScore(int limit) {
    // Fetch all posts
    List<Post> allPosts = mongoTemplate.findAll(Post.class);

    // Calculate the score for each post
    allPosts.forEach(post -> {
      post.updateScore();
    });

    // Sort the posts by score in descending order and limit the results
    return allPosts.stream()
        .sorted(Comparator.comparingDouble(Post::getScore).reversed())
        .limit(limit)
        .collect(Collectors.toList());
  }

  @Override
  public List<Post> findPostsByNewest(int limit) {
    Query query = new Query().with(Sort.by(Sort.Order.desc("createdAt"))).limit(limit);
    return mongoTemplate.find(query, Post.class);
  }

  @Override
  public <S extends Post> S insert(S entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insert'");
  }

  @Override
  public <S extends Post> List<S> insert(Iterable<S> entities) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insert'");
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public <S extends Post> List<S> saveAll(Iterable<S> entities) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
  }

  @Override
  public List<Post> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public List<Post> findAllById(Iterable<String> ids) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
  }

  @Override
  public boolean existsById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'existsById'");
  }

  @Override
  public long count() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'count'");
  }

  @Override
  public void delete(Post entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public void deleteAllById(Iterable<? extends String> ids) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAllById'");
  }

  @Override
  public void deleteAll(Iterable<? extends Post> entities) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
  }

  @Override
  public void deleteAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
  }

  @Override
  public List<Post> findAll(Sort sort) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Page<Post> findAll(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public <S extends Post> Optional<S> findOne(Example<S> example) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findOne'");
  }

  @Override
  public <S extends Post> Page<S> findAll(Example<S> example, Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public <S extends Post> long count(Example<S> example) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'count'");
  }

  @Override
  public <S extends Post> boolean exists(Example<S> example) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'exists'");
  }

  @Override
  public <S extends Post, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findBy'");
  }
}
