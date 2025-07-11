package com.tripgg.server.country.service;

import java.util.List;

import com.tripgg.server.country.response.CountryResponse;

public interface CountryService {

  List<CountryResponse> findAll();

}
