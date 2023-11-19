package com.Cafe.menu.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.menu.common.OrderUnit;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "ingredient")
public class Ingredient extends BaseEntity {
    @Column
    private String name;

    @Column
    private long stock;

    @Column
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_unit")
    private OrderUnit orderUnit;
}
