package com.Cafe.supply.common;

import lombok.Data;

@Data
public class SupplyIngredientDto {
    private long amount;
    private long price;
    private long ingredientId;


    public SupplyIngredientDto(long neededAmount, long price) {
        this.amount = neededAmount;
        this.price = price;
    }
}
