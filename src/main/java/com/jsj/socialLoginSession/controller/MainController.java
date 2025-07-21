package com.jsj.socialLoginSession.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    @GetMapping({"/main","/"})
    public String showMainPage(Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // DefaultOAuth2User 가 Principal로 온다.
        log.info(authentication.getPrincipal());
        return "/main";
    }

}
