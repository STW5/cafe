package com.Cafe.menu.repository;

import com.Cafe.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByActive(boolean active);
}
