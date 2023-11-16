package com.Cafe.menu.repository;

import com.Cafe.menu.common.Category;
import com.Cafe.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByActive(boolean active);

    public List<Menu> findAllByNameContainingAndActive(String name, boolean active);

    List<Menu> findAllByNameContainingAndCategoryAndActive(String name, Category category, boolean active);

    List<Menu> findAllByCategoryAndActive(Category category, boolean active);
}
