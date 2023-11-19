package com.Cafe.supplier.entity;

import com.Cafe.config.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "supplier")
public class Supplier extends BaseEntity {
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private boolean active;


}
