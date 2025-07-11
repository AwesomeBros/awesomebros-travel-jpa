package com.tripgg.server.city.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tripgg.server.city.entity.City;
import com.tripgg.server.city.repository.CityRepository;
import com.tripgg.server.city.response.CityResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

  private final CityRepository cityRepository;

  @Override
  public List<CityResponse> findAll(int countryId) {
    List<City> countries = cityRepository.findByCountryIdOrderByNameAsc(countryId);
    List<CityResponse> response = countries.stream()
        .map(CityResponse::from)
        .toList();
    return response;
  }

}
