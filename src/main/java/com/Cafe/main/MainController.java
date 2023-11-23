package com.Cafe.main;

import com.Cafe.menu.common.Category;
import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.service.MenuService;
import com.Cafe.order.service.OrderService;
import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MenuService menuService;
    private final OrderService orderService; // 추가


    @GetMapping("/")
    public String main(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "category", required = false) Category category,
                       Model model, HttpSession httpSession) {
        if(loginUser != null){
            model.addAttribute("loginUser", loginUser);
        }

        List<Menu> menuList = menuService.getAllSearchedMenu(search, category);
        model.addAttribute("menuList", menuList);
        model.addAttribute("category", category);

        List<Object[]> topMenuByQuantity = orderService.findTopMenuByQuantity(); // 개인과제2 때문에 추가
        model.addAttribute("topMenuByQuantity", topMenuByQuantity); // 개인과제2 때문에 추가

        return "main";
    }
}

