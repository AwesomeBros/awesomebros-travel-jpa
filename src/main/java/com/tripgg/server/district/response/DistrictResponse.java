package com.tripgg.server.district.response;

import com.tripgg.server.city.response.CityResponse;
import com.tripgg.server.district.entity.District;

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
public class DistrictResponse {
  private int id;
  private String name;
  private CityResponse cities;

  public static DistrictResponse from(District district) {
    return DistrictResponse.builder()
        .id(district.getId())
        .name(district.getName())
        .cities(CityResponse.from(district.getCity()))
        .build();
  }
}
