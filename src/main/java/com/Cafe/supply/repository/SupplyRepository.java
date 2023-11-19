package com.Cafe.supply.repository;

import com.Cafe.supply.entity.Supplier;
import com.Cafe.supply.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    public List<Supply> findAllByConfirmed(boolean confirmed);

    public Supply findBySupplierAndConfirmed(Supplier supplier, boolean confirmed);
}
