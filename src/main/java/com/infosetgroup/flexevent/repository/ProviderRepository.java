package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findByType(int type);
    List<Provider> findByTypeIsNot(int type);
    Provider findByCode(String code);
}
