package com.Cafe.cart.common;

import com.Cafe.order.common.OrderMenuDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CartMenuDto {
    private Long menuId;
    private Long userId;
    private int quantity;

    public CartMenuDto setUserId(long userId){
        this.userId = userId;
        return this;
    }
}
