package com.tripgg.server.district.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripgg.server.district.entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
  List<District> findByCityIdOrderByNameAsc(int cityId);
}
