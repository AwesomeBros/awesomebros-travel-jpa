package com.tripgg.server.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripgg.server.country.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
