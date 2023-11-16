package com.Cafe.main;

import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    @GetMapping("/")
    public String main(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if(loginUser != null){
            model.addAttribute("loginUser", loginUser);
            //orderService.createPrepairingOrder(loginUser.getId());
        }

        return "main";
    }

}
