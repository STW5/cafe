package com.Cafe.user.controller;

import com.Cafe.user.common.UserJoinForm;
import com.Cafe.user.entity.User;
import com.Cafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String joinForm(){
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(UserJoinForm userJoinForm, Model model){
        User user = userService.join(userJoinForm);
        if(user == null) return "user/joinForm";
        return "redirect:/";
    }
}
