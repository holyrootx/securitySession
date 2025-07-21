package com.jsj.socialLoginSession.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AuthController {


    @GetMapping("/auth/loginForm")
    public String showLoginForm(){

        return "/auth/loginForm";
    }

    @GetMapping("/auth/joinForm")
    public String showJoinForm(){

        return "/auth/joinForm";
    }

    @GetMapping("/auth/joinExtra")
    public String showJoinExtra(HttpServletRequest request, Model model){
        model.addAttribute("email",request.getSession().getAttribute("email"));
        model.addAttribute("name",request.getSession().getAttribute("name"));
        return "/auth/joinExtra";
    }


}
