package com.tripgg.server.district.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tripgg.server.district.entity.District;
import com.tripgg.server.district.repository.DistrictRepository;
import com.tripgg.server.district.response.DistrictResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

  private final DistrictRepository districtRepository;

  @Override
  public List<DistrictResponse> findAll(int cityId) {
    List<District> districts = districtRepository.findByCityIdOrderByNameAsc(cityId);
    List<DistrictResponse> response = districts.stream()
        .map(DistrictResponse::from)
        .toList();
    return response;
  }

}
