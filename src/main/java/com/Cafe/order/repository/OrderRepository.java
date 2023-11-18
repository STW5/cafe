package com.Cafe.order.repository;

import com.Cafe.order.common.OrderState;
import com.Cafe.order.entity.Order;
import com.Cafe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByUser(User user);


    public Optional<Order> findOneByUserAndOrderState(User user, OrderState orderState);

    List<Order> findByUserAndOrderState(User user, OrderState orderState);
}
