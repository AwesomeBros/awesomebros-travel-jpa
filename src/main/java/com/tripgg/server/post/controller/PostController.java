package com.tripgg.server.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripgg.server.global.annotation.CurrentUser;
import com.tripgg.server.global.api.Api;
import com.tripgg.server.global.jwt.JwtPayload;
import com.tripgg.server.global.message.ResponseMessage;
import com.tripgg.server.post.request.PostFilterRequest;
import com.tripgg.server.post.request.PostRequest;
import com.tripgg.server.post.response.PostListResponse;
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

  /**
   * 전체 & 국가별 & 도시별 & 지역별 게시물 조회
   * 
   * @param request 필터 요청 (PostFilterRequest)
   * @return 전체 & 국가별 & 도시별 & 지역별 게시물 목록 응답 (PostListResponse)
   */
  @GetMapping("all")
  public Api<PostListResponse> findPostsAll(@ModelAttribute PostFilterRequest request) {
    PostListResponse response = postService.findPostsAll(request);
    return Api.OK(response);
  }

  /**
   * 메인 인기 & 최신 게시물 조회
   * 
   * @param sort 정렬 기준 (popular, latest)
   * @return 인기 & 최신 게시물 목록 응답 (List<PostResponse>)
   */
  @GetMapping("sort")
  public Api<List<PostResponse>> findPostsAllBySort(@RequestParam("sort") String sort) {
    List<PostResponse> response = postService.findPostsAllBySort(sort);
    return Api.OK(response);
  }

  /**
   * 메인 도시별 게시물 조회
   * 
   * @param city 도시 기준 (city name)
   * @return 도시별 게시물 목록 응답 (List<PostResponse>)
   */
  @GetMapping("city")
  public Api<List<PostResponse>> findPostsAllByCity(@RequestParam("city") String city) {
    List<PostResponse> response = postService.findPostsAllByCity(city);
    return Api.OK(response);
  }

  /**
   * 게시물 상세 조회
   * 
   * @param id 게시물 ID
   * @return 게시물 응답 (PostResponse)
   */
  @GetMapping("{id}")
  public Api<PostResponse> findPostById(@PathVariable("id") int id) {
    PostResponse response = postService.findPostById(id);
    return Api.OK(response);
  }

  /**
   * 게시물 등록
   * 
   * @param PostRequest, user
   * @return 게시물 응답 (PostResponse)
   */
  @PostMapping
  public Api<PostResponse> createPost(@RequestBody PostRequest request, @CurrentUser JwtPayload user) {
    PostResponse response = postService.createPost(request, user.getId());
    return Api.OK(response, ResponseMessage.CREATE_POST_SUCCESS);
  }
}
