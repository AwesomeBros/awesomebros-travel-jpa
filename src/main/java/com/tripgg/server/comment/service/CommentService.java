package com.tripgg.server.comment.service;

import com.tripgg.server.comment.request.CommentFilterRequest;
import com.tripgg.server.comment.response.CommentListResponse;

public interface CommentService {

  /**
   * 게시물당 댓글 조회
   * 
   * @param request CommentFilterRequest
   * @return 댓글 목록 응답 (CommentListResponse)
   */
  CommentListResponse findCommentsByPostId(CommentFilterRequest request);

}
