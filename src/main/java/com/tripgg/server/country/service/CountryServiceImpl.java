package com.tripgg.server.country.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tripgg.server.country.entity.Country;
import com.tripgg.server.country.repository.CountryRepository;
import com.tripgg.server.country.response.CountryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

  private final CountryRepository countryRepository;

  @Override
  public List<CountryResponse> findAll() {
    List<Country> countries = countryRepository.findAll();
    List<CountryResponse> response = countries.stream()
        .map(CountryResponse::from)
        .toList();
    return response;
  }

}
