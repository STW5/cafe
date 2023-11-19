package com.Cafe.menu.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipeDto {
    private long requiredAmount;
    private long ingredientId;
    private long menuId;
}
