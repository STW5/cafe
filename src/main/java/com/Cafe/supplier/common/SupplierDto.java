package com.Cafe.supplier.common;

import com.Cafe.supplier.entity.Supplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {
    private String name;
    private String adress;

    public Supplier toEntity(){
        return Supplier.builder()
                .name(name)
                .address(adress)
                .active(true)
                .build();
    }
}
