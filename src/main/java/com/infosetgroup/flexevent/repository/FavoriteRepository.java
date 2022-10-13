package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.AppAccount;
import com.infosetgroup.flexevent.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByAppAccount(AppAccount account);
}
