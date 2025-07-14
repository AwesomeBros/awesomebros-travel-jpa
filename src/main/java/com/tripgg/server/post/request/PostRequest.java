package com.tripgg.server.post.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tripgg.server.city.entity.City;
import com.tripgg.server.country.entity.Country;
import com.tripgg.server.district.entity.District;
import com.tripgg.server.location.request.LocationRequest;
import com.tripgg.server.post.entity.Post;
import com.tripgg.server.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {

  @JsonProperty("cities_id")
  private int cityId;

  private String content;

  @JsonProperty("countries_id")
  private int countryId;

  @JsonProperty("districts_id")
  private int districtId;

  private List<LocationRequest> locations;

  private String slug;

  private String title;

  private String url;

  public Post toEntity(City city, Country country, District district, User user) {
    return Post.builder()
        .city(city)
        .content(content)
        .country(country)
        .district(district)
        .slug(slug)
        .title(title)
        .url(url)
        .user(user)
        .build();
  }
}
