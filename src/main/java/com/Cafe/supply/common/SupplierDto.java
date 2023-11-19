package com.Cafe.supply.common;

import com.Cafe.supply.entity.Supplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {
    private String name;
    private String address;

    public Supplier toEntity(){
        return Supplier.builder()
                .name(name)
                .address(address)
                .active(true)
                .build();
    }
}
