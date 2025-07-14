package com.tripgg.server.location.request;

import com.tripgg.server.location.entity.Location;
import com.tripgg.server.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LocationRequest {
  private double lat;
  private double lng;
  private String name;

  public Location toEntity(Post post) {
    return Location.builder().lat(lat).lng(lng).name(name).post(post).build();
  }
}
