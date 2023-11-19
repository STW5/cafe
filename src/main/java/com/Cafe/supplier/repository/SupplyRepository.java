package com.Cafe.supplier.repository;

import com.Cafe.supplier.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
