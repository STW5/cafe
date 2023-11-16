package com.Cafe.menu.controller;

import com.Cafe.menu.common.MenuDto;
import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.service.MenuService;
import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update")
    public String updateMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, MenuDto menuDto, Long menuId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.updateMenu(menuId, menuDto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/delete")
    public String deleteMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, long menuId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.deleteMenu(menuId);
        return "redirect:/menu/manage";
    }

    @GetMapping
    public String menuInfo(@RequestParam("menuId") Long menuId, @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        Menu menu = menuService.getMenuById(menuId);
        if(loginUser!=null)model.addAttribute(loginUser);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("menu", menu);
        return "menu/info";
    }

}
