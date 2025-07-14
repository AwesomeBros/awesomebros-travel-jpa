package com.tripgg.server.comment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tripgg.server.comment.entity.Comment;
import com.tripgg.server.comment.repository.CommentRepository;
import com.tripgg.server.comment.request.CommentFilterRequest;
import com.tripgg.server.comment.response.CommentListResponse;
import com.tripgg.server.comment.response.CommentResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  /**
   * 게시물당 댓글 조회
   * 
   * @param request CommentFilterRequest
   * @return 댓글 목록 응답 (CommentListResponse)
   */
  @Override
  public CommentListResponse findCommentsByPostId(CommentFilterRequest request) {
    int page = Math.max(0, request.getPage() - 1);
    Pageable pageable = PageRequest.of(page, request.getLimit());
    Page<Comment> commentPage = commentRepository.findByPost_Id(request.getPostId(), pageable);
    List<CommentResponse> comments = commentPage.getContent().stream()
        .map(CommentResponse::from)
        .collect(Collectors.toList());

    return CommentListResponse.builder()
        .data(comments)
        .page(request.getPage())
        .totalCount((int) commentPage.getTotalElements())
        .totalPage(commentPage.getTotalPages())
        .build();
  }

}
