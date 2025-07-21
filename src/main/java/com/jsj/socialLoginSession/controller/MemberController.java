package com.jsj.socialLoginSession.controller;

import com.jsj.socialLoginSession.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/profile/{id:\\d+}")
    public String showMemberProfile(@PathVariable("id") Long id, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getPrincipal());

        if (authentication instanceof OAuth2AuthenticationToken) {
            String provider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
            log.info("provider  : {} ",provider);
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            log.info("FormLogin ");
        }

        return "member/profile";
    }
}
