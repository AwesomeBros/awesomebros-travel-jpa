package com.tripgg.server.post.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponse {
  private int page;
  private List<PostResponse> data;
  private int totalCount;
  private int totalPage;
  private int limit;

  public PostListResponse from(List<PostResponse> data, int page, int totalCount, int totalPage, int limit) {
    return PostListResponse.builder()
        .page(page)
        .data(data)
        .totalCount(totalCount)
        .totalPage(totalPage)
        .limit(limit)
        .build();
  }
}
