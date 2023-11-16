package com.Cafe.menu.controller;

import com.Cafe.menu.common.MenuDto;
import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.service.MenuService;
import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/manage")
    public String manageMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        model.addAttribute("loginUser", loginUser);

        List<Menu> menuList = menuService.getAllMenus();
        model.addAttribute("menuList", menuList);

        return "menu/manage";
    }

    @PostMapping("/add")
    public String createMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, MenuDto menuDto){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.createMenu(menuDto);
        return "redirect:/menu/manage";
    }
}
