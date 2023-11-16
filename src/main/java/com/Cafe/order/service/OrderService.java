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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        order.setOrderedTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) return null;
        return orderRepository.findByUser(user);
    }


    public Order confirmOrder(Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) return null;
        Optional<Order> optionalOrder = orderRepository.findOneByUser(user);
        return processConfirmOrder(optionalOrder);
    }

    private Order processConfirmOrder(Optional<Order> optionalOrder) {
        if (optionalOrder.isEmpty()) return null;
        Order order = optionalOrder.get();
        long totalAmount = 0L;
        for (OrderMenu orderMenu : order.getOrderMenus()) {
            totalAmount += orderMenu.getQuantity()*orderMenu.getMenu().getPrice();
            if (!menuService.checkStockAvailable(orderMenu)) return null;
        }
        order.getOrderMenus().forEach(menuService::subIngredientStock);

        order.setTotalAmount(totalAmount);
        //order.setPaymentMethod(paymentMethod);
        order.setOrderedTime(LocalDateTime.now());
        order.setOrderState(OrderState.ORDER);
        return orderRepository.save(order);
    }
}
