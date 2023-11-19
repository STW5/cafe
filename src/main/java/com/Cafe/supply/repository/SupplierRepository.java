package com.Cafe.supply.repository;

import com.Cafe.supply.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    public List<Supplier> findAllByActive(boolean active);

    boolean existsByNameAndActive(String name, boolean active);
}
