package com.Cafe.menu.repository;

import com.Cafe.menu.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    public List<Ingredient> findAllByActive(boolean active);

    public boolean existsByNameAndActive(String name, boolean active);
}
