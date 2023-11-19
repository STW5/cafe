package com.Cafe.supply.service;

import com.Cafe.menu.repository.IngredientRepository;
import com.Cafe.menu.service.MenuService;
import com.Cafe.supply.common.SupplierDto;
import com.Cafe.supply.common.SupplyIngredientDto;
import com.Cafe.supply.entity.Supplier;
import com.Cafe.supply.entity.Supply;
import com.Cafe.supply.entity.SupplyIngredient;
import com.Cafe.supply.repository.SupplierRepository;
import com.Cafe.supply.repository.SupplyIngredientRepository;
import com.Cafe.supply.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplierRepository supplierRepository;
    private final SupplyRepository supplyRepository;
    private final SupplyIngredientRepository supplyIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final MenuService menuService;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAllByActive(true);
    }

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAllByConfirmed(true);
    }

    public Supplier addSupplier(SupplierDto supplierDto) {
        if(supplierRepository.existsByNameAndActive(supplierDto.getName(), true)) return null;
        return supplierRepository.save(supplierDto.toEntity());
    }


    public void updateSupplier(long supplierId, SupplierDto supplierDto) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if(optionalSupplier.isEmpty()) return;
        Supplier supplier = optionalSupplier.get();
        supplier.setName(supplierDto.getName());
        supplier.setAddress(supplierDto.getAddress());
        supplierRepository.save(supplier);
    }

    @Transactional
    public void deActivateSupplier(long supplierId) {
        supplierRepository.findById(supplierId).ifPresent(supplier -> supplier.setActive(false));
    }

    public Supplier getSupplier(long supplierId) {
        return supplierRepository.findById(supplierId).orElse(null);
    }

    @Transactional
    public Supply getSupply(long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        Supply supply = supplyRepository.findBySupplierAndConfirmed(supplier, false);
        if(supply != null) return supply;
        supply = Supply.builder()
                .supplier(supplier)
                .confirmed(false)
                .build();
        return supplyRepository.save(supply);
    }

    @Transactional
    public SupplyIngredient purchaseSupplyIngredient(long supplierId, SupplyIngredientDto supplyIngredientDto) {
        Supply supply = getSupply(supplierId);
        return supplyIngredientRepository.save(SupplyIngredient.builder()
                .amount(supplyIngredientDto.getAmount())
                .price(supplyIngredientDto.getPrice())
                .supply(supply)
                .ingredient(ingredientRepository.findById(supplyIngredientDto.getIngredientId()).orElseThrow())
                .build());
    }

    @Transactional
    public void confirmSupply(long supplierId, LocalDateTime suppliedDate){
        Supply supply = getSupply(supplierId);
        supply.setSuppliedDate(suppliedDate);
        supply.setConfirmed(true);

        supply.getSupplyIngredientList().forEach(supplyItem -> menuService.addIngredientStock(supplyItem.getIngredient(), supplyItem.getAmount()));

        supplyRepository.save(supply);
    }
}
