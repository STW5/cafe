package com.Cafe.menu.common;

import com.Cafe.menu.entity.Menu;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuDto {
    private String name;
    private long price;
    private boolean featured;
    private boolean recommended;
    //private boolean special;
    private Category category;

    // dto -> entity
    public Menu toEntity(){
        return Menu.builder()
                .name(name)
                .price(price)
                .active(true)
                .featured(featured)
                .recommended(recommended)
                .category(category)
                .build();
    }
}
