package com.tripgg.server.comment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripgg.server.comment.request.CommentFilterRequest;
import com.tripgg.server.comment.response.CommentListResponse;
import com.tripgg.server.comment.service.CommentService;
import com.tripgg.server.global.api.Api;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentController {

  private final CommentService commentService;

  /**
   * 게시물당 댓글 조회
   * 
   * @param request CommentFilterRequest
   * @return 댓글 목록 응답 (CommentListResponse)
   */
  @GetMapping("all")
  public Api<CommentListResponse> findCommentsByPostId(@ModelAttribute CommentFilterRequest request) {
    CommentListResponse response = commentService.findCommentsByPostId(request);
    return Api.OK(response);
  }
}
