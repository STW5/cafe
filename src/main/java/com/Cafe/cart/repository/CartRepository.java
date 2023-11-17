package com.Cafe.cart.repository;

import com.Cafe.cart.entity.Cart;
import com.Cafe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public Cart findByUserId(Long id);

    public List<Cart> findByUser(User user);
}
