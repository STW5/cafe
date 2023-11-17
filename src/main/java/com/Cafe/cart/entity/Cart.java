package com.Cafe.cart.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.user.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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


}
