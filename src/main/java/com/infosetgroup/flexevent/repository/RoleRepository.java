package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(int type);
}
