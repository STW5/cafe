package com.Cafe.order.repository;

import com.Cafe.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
