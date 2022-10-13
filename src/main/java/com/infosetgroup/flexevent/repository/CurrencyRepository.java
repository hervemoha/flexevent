package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByName(String name);
}
