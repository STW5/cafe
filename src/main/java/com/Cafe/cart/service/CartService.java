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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMenuRepository cartMenuRepository;
    private final UserService userService;
    private final MenuService menuService;


    public void addCart(CartMenuDto cartMenuDto) {
        User user = userService.getUserById(cartMenuDto.getUserId());
        //if (user == null) return null;
        Menu menu = menuService.getMenuById(cartMenuDto.getMenuId());
        //if (menu == null) return null;
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null){
            cart = createCart(user);
            cartRepository.save(cart);
        }
        CartMenu cartMenu = cartMenuRepository.findByCartIdAndMenuId(cart.getId(), menu.getId());

        if (cartMenu == null) {
            cartMenu = cartMenu.createCartMenu(cart, menu, cartMenuDto.getQuantity());
            cartMenuRepository.save(cartMenu);
        } else {
            CartMenu update = cartMenu;
            update.setCart(cartMenu.getCart());
            update.setMenu(cartMenu.getMenu());
            update.addQuantity(cartMenuDto.getQuantity());
            update.setQuantity(update.getQuantity());
            cartMenuRepository.save(update);
        }


    }

    private Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreateTime(LocalDateTime.now());
        return cartRepository.save(cart);
    }


    public List<CartMenu> getAllCartList(Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) return null;

        return cartMenuRepository.findByCartId(userId);
    }

    public void removeCartMenu(long cartMenuId) {
        Optional<CartMenu> optionalCartMenu = cartMenuRepository.findById(cartMenuId);
        if(optionalCartMenu.isEmpty()) return;
        CartMenu cartMenu = optionalCartMenu.get();
        cartMenuRepository.delete(cartMenu);
    }

    public CartMenu changeQuantity(long cartMenuId, int quantity) {
        Optional<CartMenu> optionalCartMenu = cartMenuRepository.findById(cartMenuId);
        if(optionalCartMenu.isEmpty()) return null;
        if(quantity<=0){
            removeCartMenu(cartMenuId);
            return null;
        }
        CartMenu cartMenu = optionalCartMenu.get();
        cartMenu.setQuantity(quantity);
        return cartMenuRepository.save(cartMenu);
    }
}
