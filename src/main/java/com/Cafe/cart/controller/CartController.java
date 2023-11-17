package com.Cafe.cart.controller;

import com.Cafe.cart.common.CartMenuDto;
import com.Cafe.cart.entity.Cart;
import com.Cafe.cart.entity.CartMenu;
import com.Cafe.cart.service.CartService;
import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public String addCart(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                          @ModelAttribute CartMenuDto cartMenuDto,
                        HttpServletRequest httpServletRequest) {
        if(loginUser==null) return "redirect:/user/login";
        cartService.addCart(cartMenuDto.setUserId(loginUser.getId()));
        return "redirect:/";
    }

    @GetMapping("/list")
    public String cartList(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if(loginUser==null) return "redirect:/user/login";

        List<CartMenu> cartMenuList = cartService.getAllCartList(loginUser.getId());

        model.addAttribute("cartMenuList", cartMenuList);
        model.addAttribute("loginUser", loginUser);
        return "order/cartList";
    }
}
