package com.Cafe.cart.repository;

import com.Cafe.cart.entity.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {
    CartMenu findByCartIdAndMenuId(Long cartId, Long menuId);
}
