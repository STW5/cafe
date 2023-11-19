package com.Cafe.supply.common;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SupplyDto {
    private long supplierId;
    private List<SupplyIngredientDto> supplyItemDTOList;
    private LocalDateTime suppliedDate;
}
