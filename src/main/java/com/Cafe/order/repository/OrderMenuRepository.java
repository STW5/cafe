package com.Cafe.order.repository;

import com.Cafe.order.entity.Order;
import com.Cafe.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findByOrder(Order order);
}
