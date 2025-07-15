package com.jsj.socialLoginSession.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AuthController {


    @GetMapping({"/main","/"})
    public String showMainPage(){

        return "/main";
    }

    @GetMapping("/auth/loginForm")
    public String showLoginForm(){

        return "/auth/loginForm";
    }

    @GetMapping("/auth/joinForm")
    public String showJoinForm(){

        return "/auth/joinForm";
    }
}
