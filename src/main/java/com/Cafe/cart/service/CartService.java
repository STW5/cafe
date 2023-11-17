package com.Cafe.cart.service;

import com.Cafe.cart.common.CartMenuDto;
import com.Cafe.cart.entity.Cart;
import com.Cafe.cart.entity.CartMenu;
import com.Cafe.cart.repository.CartMenuRepository;
import com.Cafe.cart.repository.CartRepository;
import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.service.MenuService;
import com.Cafe.user.entity.User;
import com.Cafe.user.repository.UserRepository;
import com.Cafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMenuRepository cartMenuRepository;
    private final UserService userService;
    private final MenuService menuService;


    public CartMenu addCart(CartMenuDto cartMenuDto) {
        User user = userService.getUserById(cartMenuDto.getUserId());
        if (user == null) return null;
        Menu menu = menuService.getMenuById(cartMenuDto.getMenuId());
        if (menu == null) return null;
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) return null;
        CartMenu cartMenu = cartMenuRepository.findByCartIdAndMenuId(cart.getId(), menu.getId());

        cartMenu = createCartItem(cart, menu, cartMenuDto.getQuantity());
        return cartMenuRepository.save(cartMenu);
    }

    private CartMenu createCartItem(Cart cart, Menu menu, int quantity) {
        CartMenu cartMenu = new CartMenu();
        cartMenu.setCart(cart);
        cartMenu.setMenu(menu);
        cartMenu.setQuantity(quantity);
        return cartMenu;
    }

    public List<Cart> getAllCartList(Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) return null;
        return cartRepository.findByUser(user);
    }
}
