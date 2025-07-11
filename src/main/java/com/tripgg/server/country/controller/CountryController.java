package com.tripgg.server.country.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripgg.server.country.response.CountryResponse;
import com.tripgg.server.country.service.CountryService;
import com.tripgg.server.global.api.Api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("countries")
@RequiredArgsConstructor
public class CountryController {

  private final CountryService countryService;

  @GetMapping
  public Api<List<CountryResponse>> findCountriesAll() {
    List<CountryResponse> response = countryService.findAll();
    return Api.OK(response);
  }
}
