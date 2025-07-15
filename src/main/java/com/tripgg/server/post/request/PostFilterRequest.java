package com.tripgg.server.post.request;

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
@ToString
@Builder
public class PostFilterRequest {

  private String country;

  private String city;

  private String district;

  private int page = 1;

  private int limit = 12;

  private String title;
}
