package com.Cafe.menu.common;

import com.Cafe.menu.entity.Ingredient;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IngredientDto {
    private String name;
    private long stock;
    private OrderUnit orderUnit;

    public Ingredient toEntity(){
        return Ingredient.builder()
                .name(name)
                .stock(stock)
                .orderUnit(orderUnit)
                .active(true)
                .build();
    }
}
