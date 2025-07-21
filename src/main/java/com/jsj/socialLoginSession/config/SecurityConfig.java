package com.jsj.socialLoginSession.config;

import com.jsj.socialLoginSession.filter.JoinCompleteCheckFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final JoinCompleteCheckFilter joinCompleteCheckFilter;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(auth->auth
                .requestMatchers(AppURLs.getCombineURL(AppURLs.PUBLIC_URLS, AppURLs.PREFIX_WHITELIST)).permitAll()
                .requestMatchers(AppURLs.getCombineURL(AppURLs.MEMBER_URLS, AppURLs.MEMBER_URLS_PREFIX)).hasRole("MEMBER")
                .requestMatchers(AppURLs.getCombineURL(AppURLs.ADMIN_URLS, AppURLs.ADMIN_URLS_PREFIX)).hasRole("ADMIN")

        ).formLogin(form->form
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/",true)
                .permitAll()
        ).oauth2Login(oauth2 -> oauth2
                .loginPage("/auth/loginForm")
                .userInfoEndpoint(user ->
                        user.userService(customOAuth2UserService)
                )
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler((req, res, ex) ->
                        res.sendRedirect("/loginForm?error")
                )
        ).logout(logout->logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)        // 세션 무효화
                        .clearAuthentication(true)           // 인증 정보 삭제
                        .deleteCookies("JSESSIONID")  // 세션 쿠키도 삭제
        ).addFilterAfter(joinCompleteCheckFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
