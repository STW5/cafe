package com.Cafe.cart.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "cart")
public class Cart extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;



    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    private List<CartMenu> cartMenus = new LinkedList<>();
}
