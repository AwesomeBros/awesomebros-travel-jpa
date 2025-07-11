package com.tripgg.server.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripgg.server.global.api.Api;
import com.tripgg.server.post.response.PostResponse;
import com.tripgg.server.post.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping("sort")
  public Api<List<PostResponse>> findPostsAllBySort(@RequestParam("sort") String sort) {
    List<PostResponse> response = postService.findPostsAllBySort(sort);
    return Api.OK(response);
  }

  @GetMapping("city")
  public Api<List<PostResponse>> findPostsAllByCity(@RequestParam("city") String city) {
    List<PostResponse> response = postService.findPostsAllByCity(city);
    return Api.OK(response);
  }
}
