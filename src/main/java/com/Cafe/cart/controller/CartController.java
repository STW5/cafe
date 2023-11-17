package com.Cafe.cart.controller;

import com.Cafe.cart.common.CartMenuDto;
import com.Cafe.cart.entity.Cart;
import com.Cafe.cart.entity.CartMenu;
import com.Cafe.cart.service.CartService;
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
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final OrderService orderService;

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
        long totalAmount = 0L;
        for(CartMenu cartMenu : cartMenuList){
            totalAmount += (long) cartMenu.getQuantity() * cartMenu.getMenu().getPrice();
        }

        model.addAttribute("cartMenuList", cartMenuList);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("totalAmount", totalAmount);
        return "order/cartList";
    }

    @PostMapping("/delete")
    public String deleteCartMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, long cartMenuId){
        if(loginUser==null)return "redirect:/user/login";
        cartService.removeCartMenu(cartMenuId);
        return "redirect:/cart/list";
    }

    @PostMapping("/update")
    public String changeQuantity(@SessionAttribute(name = "loginUser", required = false) User loginUser, long cartMenuId, int quantity){
        if(loginUser==null)return "redirect:/user/login";
        cartService.changeQuantity(cartMenuId, quantity);
        return "redirect:/cart/list";
    }

    @PostMapping("/order")
    public String orderCartMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser){
        if(loginUser == null) return "redirect:/user/login";

        // 사용자의 장바구니 항목 가져오기
        List<CartMenu> cartMenuList = cartService.getAllCartList(loginUser.getId());

        // 장바구니가 비어있지 않는 경우
        if(!cartMenuList.isEmpty()){
            // 주문 서비스에서 주문 처리하기
            orderService.createCartOrder(loginUser.getId(), cartMenuList);

            // 주문이 성공적으로 이루어진 후 장바구니 비우기
            for(CartMenu cartMenu : cartMenuList){
                cartService.removeCartMenu(cartMenu.getId());
            }
        }

        return "redirect:/order/list";
    }


}
