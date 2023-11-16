package com.Cafe.user.controller;

import com.Cafe.user.common.UserJoinForm;
import com.Cafe.user.common.UserLoginForm;
import com.Cafe.user.entity.User;
import com.Cafe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @GetMapping("/login")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginForm userLoginForm, HttpServletRequest httpServletRequest, Model model) {
        User user = userService.login(userLoginForm);
        if (user == null) {
            log.info("유저가 없음");
            return "user/loginForm";
        }
        if(!user.isActive()){
            return "user/loginForm";
        }

        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("loginUser", user);
        session.setMaxInactiveInterval(3600);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
