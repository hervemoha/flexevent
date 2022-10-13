package com.infosetgroup.flexevent.repository;


import com.infosetgroup.flexevent.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByActivated(boolean activated);
    Category findByCode(String code);
    List<Category> findByActivatedOrderByWeightAsc(boolean activated);
}
