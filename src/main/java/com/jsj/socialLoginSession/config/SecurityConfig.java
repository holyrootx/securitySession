package com.jsj.socialLoginSession.config;

import com.jsj.socialLoginSession.oauth.CustomOAuth2UserService;
import com.jsj.socialLoginSession.oauth.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.jsj.socialLoginSession.util.AppURLs;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
         .csrf(csrf-> csrf
                .disable()
        ).authorizeHttpRequests(auth->auth
                .requestMatchers(AppURLs.getCombineURL(AppURLs.PUBLIC_URLS, AppURLs.PREFIX_WHITELIST)).permitAll()
                .requestMatchers(AppURLs.getCombineURL(AppURLs.MEMBER_URLS, AppURLs.MEMBER_URLS_PREFIX)).hasRole("MEMBER")
                .requestMatchers(AppURLs.getCombineURL(AppURLs.ADMIN_URLS, AppURLs.ADMIN_URLS_PREFIX)).hasRole("ADMIN")

        ).formLogin(form->form
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/",true)
                .permitAll()
        ).oauth2Login(oauth2 -> oauth2
                .loginPage("/loginForm")
                .userInfoEndpoint(user ->
                        user.userService(customOAuth2UserService)
                )
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler((req, res, ex) ->
                        res.sendRedirect("/loginForm?error")
                )
        );


        return http.build();
    }
}
