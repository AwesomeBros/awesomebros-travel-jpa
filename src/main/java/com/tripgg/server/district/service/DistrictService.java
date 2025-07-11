package com.tripgg.server.district.service;

import java.util.List;

import com.tripgg.server.district.response.DistrictResponse;

public interface DistrictService {

  List<DistrictResponse> findAll(int cityId);

}
