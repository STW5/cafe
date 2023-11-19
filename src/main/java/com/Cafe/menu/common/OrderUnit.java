package com.Cafe.menu.common;

import lombok.Getter;

@Getter
public enum OrderUnit {
    GRAM("g"), MILLILITER("ml"), NUMBER("개");

    private final String displayName;
    OrderUnit(String displayName) {
        this.displayName = displayName;
    }
}
