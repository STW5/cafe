package com.Cafe.order.repository;

import com.Cafe.order.entity.Order;
import com.Cafe.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findByOrder(Order order);

    @Query("SELECT o.menu, SUM(o.quantity) as totalQuantity FROM OrderMenu o GROUP BY o.menu ORDER BY totalQuantity DESC")
    List<Object[]> findTopMenuByQuantity();
}