package com.tripgg.server.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tripgg.server.post.entity.Post;
import com.tripgg.server.post.repository.PostQueryRepository;
import com.tripgg.server.post.repository.PostRepository;
import com.tripgg.server.post.response.PostResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostQueryRepository postQueryRepository;

  @Override
  public List<PostResponse> findPostsAllBySort(String sort) {
    List<Post> posts = postQueryRepository.findAllBySort(sort);
    List<PostResponse> response = posts.stream()
        .map(PostResponse::from)
        .toList();
    return response;
  }

  @Override
  public List<PostResponse> findPostsAllByCity(String city) {
    List<Post> posts = postQueryRepository.findAllByCity(city);
    List<PostResponse> response = posts.stream()
        .map(PostResponse::from)
        .toList();
    return response;
  }

}
