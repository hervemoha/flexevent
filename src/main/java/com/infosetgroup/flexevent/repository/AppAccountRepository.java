package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.AppAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppAccountRepository extends JpaRepository<AppAccount, Long> {
    AppAccount findByUsername(String username);
}
