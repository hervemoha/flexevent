package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findByActivatedOrderByNameAsc(boolean activated);
    Country findByCode(String code);
}
