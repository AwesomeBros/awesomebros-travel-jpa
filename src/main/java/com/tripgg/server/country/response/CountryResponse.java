package com.tripgg.server.country.response;

import com.tripgg.server.country.entity.Country;

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
public class CountryResponse {
  private int id;
  private String name;

  public static CountryResponse from(Country country) {
    return CountryResponse.builder()
        .id(country.getId())
        .name(country.getName())
        .build();
  }
}
