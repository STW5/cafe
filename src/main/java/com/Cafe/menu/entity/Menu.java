package com.Cafe.menu.entity;

import com.Cafe.config.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Table(name = "menu")
public class Menu extends BaseEntity {
    @Column
    private String name;
    @Column
    private long price;
    @Column
    private boolean active;
    @Column
    private boolean special;
}
