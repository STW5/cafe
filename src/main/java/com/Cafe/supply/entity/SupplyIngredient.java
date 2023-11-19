package com.Cafe.supply.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.menu.entity.Ingredient;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "supply_list")
public class SupplyIngredient extends BaseEntity {
    private long amount;
    private long price;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;
}
