package com.Cafe.cart.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.menu.entity.Menu;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@Builder
@Table(name = "cart_menu")
public class CartMenu extends BaseEntity {
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public static CartMenu createCartMenu(Cart cart, Menu menu, int quantity) {
        CartMenu cartMenu = new CartMenu();
        cartMenu.setCart(cart);
        cartMenu.setMenu(menu);
        cartMenu.setQuantity(quantity);
        return cartMenu;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}

