package com.Cafe.order.controller;

import com.Cafe.order.common.OrderMenuDto;
import com.Cafe.order.entity.Order;
import com.Cafe.order.entity.OrderMenu;
import com.Cafe.order.service.OrderService;
import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public String order(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                        @ModelAttribute OrderMenuDto orderMenuDto,
                        HttpServletRequest httpServletRequest,
                        Model model){
        if(loginUser==null)return "redirect:/user/login";
        OrderMenu orderMenu = orderService.order(orderMenuDto.setUserId(loginUser.getId()));
        long totalAmount = orderMenu.getQuantity() * orderMenu.getMenu().getPrice();
        model.addAttribute("orderMenu", orderMenu);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("loginUser", loginUser);
        return "/order/order";
    }

    @GetMapping("/confirm")
    public String confirmOrder(@SessionAttribute(name = "loginUser", required = false) User loginUser){
        if (loginUser == null) return "redirect:/user/login";
        orderService.confirmOrder(loginUser.getId());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String orderList(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if(loginUser==null)return "redirect:/user/login";
        List<Order> orderList = orderService.getAllOrders(loginUser.getId());
        model.addAttribute("orderList", orderList);
        model.addAttribute("loginUser", loginUser);
        return "order/list";
    }

}
