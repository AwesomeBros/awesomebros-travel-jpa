package com.tripgg.server.district.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripgg.server.district.response.DistrictResponse;
import com.tripgg.server.district.service.DistrictService;
import com.tripgg.server.global.api.Api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("districts")
@RequiredArgsConstructor
public class DistrictsController {

  private final DistrictService districtService;

  @GetMapping
  public Api<List<DistrictResponse>> findDistrictsAll(@RequestParam("cities_id") int cityId) {
    List<DistrictResponse> response = districtService.findAll(cityId);
    return Api.OK(response);
  }
}
