package com.Cafe.supply.entity;

import com.Cafe.config.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "supply")
public class Supply extends BaseEntity {
    private LocalDateTime suppliedDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "supply", orphanRemoval = true)
    private List<SupplyIngredient> supplyIngredientList = new LinkedList<>();

    private boolean confirmed;
}
