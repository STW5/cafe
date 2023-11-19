package com.Cafe.order.service;

import com.Cafe.cart.entity.CartMenu;
import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.service.MenuService;
import com.Cafe.order.common.OrderMenuDto;
import com.Cafe.order.common.OrderState;
import com.Cafe.order.common.PaymentMethod;
import com.Cafe.order.entity.Order;
import com.Cafe.order.entity.OrderMenu;
import com.Cafe.order.repository.OrderMenuRepository;
import com.Cafe.order.repository.OrderRepository;
import com.Cafe.user.entity.User;
import com.Cafe.user.repository.UserRepository;
import com.Cafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MenuService menuService;

    public OrderMenu order(OrderMenuDto orderMenuDto) {
        User user = userService.getUserById(orderMenuDto.getUserId());
        if (user == null) return null;
        Menu menu = menuService.getMenuById(orderMenuDto.getMenuId());
        if (menu == null) return null;

        Order order = createOrder(user, OrderState.PREPARING);
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
        order.setOrderState(orderState.PREPARING);
        order.setOrderedTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) return null;
        return orderRepository.findByUser(user);
    }


    public Order confirmOrder(Long userId, PaymentMethod paymentMethod) {
        User user = userService.getUserById(userId);
        if(user == null) return null;
        Optional<Order> optionalOrder = orderRepository.findOneByUserAndOrderState(user, OrderState.PREPARING);
        return processConfirmOrder(paymentMethod, optionalOrder);
    }

    private Order processConfirmOrder(PaymentMethod paymentMethod, Optional<Order> optionalOrder) {
        if (optionalOrder.isEmpty()) return null;
        Order order = optionalOrder.get();
        long totalAmount = 0L;
        for (OrderMenu orderMenu : order.getOrderMenus()) {
            totalAmount += orderMenu.getQuantity()*orderMenu.getMenu().getPrice();
            //if (!menuService.checkStockAvailable(orderMenu)) return null;
        }
        order.getOrderMenus().forEach(menuService::subIngredientStock);

        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(paymentMethod);
        order.setOrderedTime(LocalDateTime.now());
        order.setOrderState(OrderState.ORDER);
        return orderRepository.save(order);
    }


    public void createCartOrder(Long userId, List<CartMenu> cartMenuList){
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.get();

        Order order = new Order();
        order.setUser(user);
        order.setOrderState(OrderState.PREPARING);
        // 주문 저장
        orderRepository.save(order);

        // 장바구니 항목을 주문 항목으로 변환하고 저장
        for(CartMenu cartMenu : cartMenuList){
            OrderMenu orderMenu = new OrderMenu();
            orderMenu.setOrder(order);
            orderMenu.setMenu(cartMenu.getMenu());
            orderMenu.setQuantity(cartMenu.getQuantity());

            // 주문 항목 저장
            orderMenuRepository.save(orderMenu);
        }
    }

    public List<Order> confirmCartOrder(Long userId, PaymentMethod paymentMethod) {
        User user = userService.getUserById(userId);
        if(user == null) return null;
        List<Order> orderList = orderRepository.findByUserAndOrderState(user, OrderState.PREPARING);
        return processConfirmCartOrder(paymentMethod, orderList);
    }

    private List<Order> processConfirmCartOrder(PaymentMethod paymentMethod, List<Order> orderList) {
        if (orderList.isEmpty()) return null;
        for (Order order : orderList) {
            long totalAmount = 0L;
            // 주문 항목을 다시 불러와서 새로운 총 금액을 계산
            List<OrderMenu> orderMenus = orderMenuRepository.findByOrder(order);
            for (OrderMenu orderMenu : orderMenus) {
                totalAmount += orderMenu.getQuantity()*orderMenu.getMenu().getPrice();
            }
            orderMenus.forEach(menuService::subIngredientStock);

            order.setTotalAmount(totalAmount);
            order.setPaymentMethod(paymentMethod);
            order.setOrderedTime(LocalDateTime.now());
            order.setOrderState(OrderState.ORDER);
            orderRepository.save(order);
        }

        return null;
    }


//    @Transactional
//    public void processOrder(Long userId, List<CartMenu> cartMenuList, PaymentMethod paymentMethod) {
//        createCartOrder(userId, cartMenuList);
//        confirmCartOrder(userId, paymentMethod);
//    }

}
