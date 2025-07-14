package com.tripgg.server.post.service;

import java.util.List;
import java.util.UUID;

import com.tripgg.server.post.request.PostRequest;
import com.tripgg.server.post.response.PostResponse;

public interface PostService {

  /**
   * 메인 인기 & 최신 게시물 조회
   * 
   * @param sort 정렬 기준 (popular, latest)
   * @return 인기 & 최신 게시물 목록 응답 (List<PostResponse>)
   */
  List<PostResponse> findPostsAllBySort(String sort);

  /**
   * 메인 도시별 게시물 조회
   * 
   * @param city 도시 기준 (city name)
   * @return 도시별 게시물 목록 응답 (List<PostResponse>)
   */
  List<PostResponse> findPostsAllByCity(String city);

  /**
   * 게시물 등록
   * 
   * @param PostRequest, user
   * @return 게시물 응답 (PostResponse)
   */
  PostResponse createPost(PostRequest request, UUID userId);

  /**
   * 게시물 상세 조회
   * 
   * @param id 게시물 ID
   * @return 게시물 응답 (PostResponse)
   */
  PostResponse findPostById(int id);

}
