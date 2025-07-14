package com.tripgg.server.location.response;

import com.tripgg.server.location.entity.Location;

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
public class LocationResponse {
  private int id;
  private double lat;
  private double lng;
  private String name;

  public static LocationResponse from(Location location) {
    return LocationResponse.builder()
        .id(location.getId())
        .lat(location.getLat())
        .lng(location.getLng())
        .name(location.getName())
        .build();
  }
}
