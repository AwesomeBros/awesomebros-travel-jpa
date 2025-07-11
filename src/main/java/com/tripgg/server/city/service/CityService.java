package com.tripgg.server.city.service;

import java.util.List;

import com.tripgg.server.city.response.CityResponse;

public interface CityService {

  List<CityResponse> findAll(int countryId);

}
