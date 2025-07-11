package com.tripgg.server.city.response;

import com.tripgg.server.city.entity.City;
import com.tripgg.server.country.response.CountryResponse;

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
public class CityResponse {
  private int id;
  private String name;
  private CountryResponse countries;

  public static CityResponse from(City city) {
    return CityResponse.builder()
        .id(city.getId())
        .name(city.getName())
        .countries(CountryResponse.from(city.getCountry()))
        .build();
  }
}
