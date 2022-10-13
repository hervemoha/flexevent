package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Configuration findByActivated(boolean activated);
}
