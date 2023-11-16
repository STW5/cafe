package com.Cafe.order.service;

import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.service.MenuService;
import com.Cafe.order.common.OrderMenuDto;
import com.Cafe.order.common.OrderState;
import com.Cafe.order.entity.Order;
import com.Cafe.order.entity.OrderMenu;
import com.Cafe.order.repository.OrderMenuRepository;
import com.Cafe.order.repository.OrderRepository;
import com.Cafe.user.entity.User;
import com.Cafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserService userService;
    private final MenuService menuService;

    public OrderMenu order(OrderMenuDto orderMenuDto) {
        User user = userService.getUserById(orderMenuDto.getUserId());
        if (user == null) return null;
        Menu menu = menuService.getMenuById(orderMenuDto.getMenuId());
        if (menu == null) return null;

        Order order = createOrder(user, OrderState.ORDER);
        OrderMenu orderMenu = OrderMenu.builder()
                .menu(menu)
                .order(order)
                .quantity(orderMenuDto.getQuantity())
                .build();
        return orderMenuRepository.save(orderMenu);
    }

    private Order createOrder(User user, OrderState orderState) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderState(orderState);
        return orderRepository.save(order);
    }
}
