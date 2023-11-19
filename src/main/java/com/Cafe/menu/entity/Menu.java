package com.Cafe.menu.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.menu.common.Category;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @OneToMany(mappedBy = "menu", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Recipe> recipes = new LinkedList<>();
}
