package com.Cafe.menu.common;

import lombok.Getter;

@Getter
public enum Category {
    COFFEE("커피"), TEA("차"), BEVERAGE("음료"), BREAD("빵"), ALL("전체");

    private final String displayName;
    Category(String displayName) {
        this.displayName = displayName;
    }
}
