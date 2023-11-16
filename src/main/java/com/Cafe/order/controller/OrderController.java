package com.Cafe.order.controller;

import com.Cafe.order.common.OrderMenuDto;
import com.Cafe.order.entity.OrderMenu;
import com.Cafe.order.service.OrderService;
import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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


}
