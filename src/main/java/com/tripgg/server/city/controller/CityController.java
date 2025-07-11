package com.tripgg.server.city.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripgg.server.city.response.CityResponse;
import com.tripgg.server.city.service.CityService;
import com.tripgg.server.global.api.Api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
public class CityController {

  private final CityService cityService;

  @GetMapping
  public Api<List<CityResponse>> findCitiesAll(@RequestParam("countries_id") int countryId) {
    List<CityResponse> response = cityService.findAll(countryId);
    return Api.OK(response);
  }
}
