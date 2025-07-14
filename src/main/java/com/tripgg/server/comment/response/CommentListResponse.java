package com.tripgg.server.comment.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentListResponse {
  private int page;
  private List<CommentResponse> data;
  private int totalCount;
  private int totalPage;

  public CommentListResponse from(List<CommentResponse> data, int page, int totalCount, int totalPage) {
    return CommentListResponse.builder()
        .page(page)
        .data(data)
        .totalCount(totalCount)
        .totalPage(totalPage)
        .build();
  }
}
