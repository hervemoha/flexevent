package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.Category;
import com.infosetgroup.flexevent.entity.Favorite;
import com.infosetgroup.flexevent.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByCode(String code);
    List<Item> findAllByCategory(Category category);
}
