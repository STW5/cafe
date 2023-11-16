package com.Cafe.order.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class OrderMenuDto {
    private Long menuId;
    private Long userId;
    private int quantity;

    public OrderMenuDto setUserId(long userId){
        this.userId = userId;
        return this;
    }
}
