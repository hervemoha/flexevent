package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.City;
import com.infosetgroup.flexevent.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByCountryAndActivatedOrderByNameAsc(Country country, boolean activated);
    City findByCode(String code);
}