package com.tripgg.server.comment.response;

import com.tripgg.server.comment.entity.Comment;
import com.tripgg.server.user.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
  private int id;
  private String content;
  private UserResponse user;
  private String createdAt;

  public static CommentResponse from(Comment comment) {
    return CommentResponse.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt().toString())
        .user(UserResponse.from(comment.getUser()))
        .build();
  }
}
