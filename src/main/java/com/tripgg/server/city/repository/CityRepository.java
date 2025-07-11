package com.tripgg.server.city.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripgg.server.city.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
  List<City> findByCountryIdOrderByNameAsc(int countryId);
}
