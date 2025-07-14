package com.tripgg.server.post.response;

import java.util.List;

import com.tripgg.server.district.response.DistrictResponse;
import com.tripgg.server.location.response.LocationResponse;
import com.tripgg.server.post.entity.Post;
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
public class PostResponse {

  private int id;
  private String title;
  private String content;
  private UserResponse users;
  private String slug;
  private int viewCount;
  private String url;
  private String createdAt;
  private DistrictResponse district;
  private List<LocationResponse> locations;

  public static PostResponse from(Post post) {
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .users(UserResponse.from(post.getUser()))
        .slug(post.getSlug())
        .url(post.getUrl())
        .viewCount(post.getViewCount())
        .createdAt(post.getCreatedAt().toString())
        .district(DistrictResponse.from(post.getDistrict()))
        .locations(post.getLocations().stream()
            .map(LocationResponse::from)
            .toList())
        .build();
  }
}
