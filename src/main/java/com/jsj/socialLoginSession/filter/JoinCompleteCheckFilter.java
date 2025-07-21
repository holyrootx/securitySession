package com.jsj.socialLoginSession.filter;

import com.jsj.socialLoginSession.entity.Member;
import com.jsj.socialLoginSession.form.PrincipalDetails;
import com.jsj.socialLoginSession.repository.MemberRepository;
import com.jsj.socialLoginSession.util.AppURLs;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class JoinCompleteCheckFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        AntPathMatcher matcher = new AntPathMatcher();
        String uri = request.getRequestURI();

        log.info("현재 경로 {}",uri);
        // 공개 경로인 경우, 다음 필터로 이동
        for (String publicPath : AppURLs.PUBLIC_URLS) {
            if (matcher.match(publicPath, uri)) {
                log.info("🎫 PUBLIC 통과 : {}", uri);
                filterChain.doFilter(request, response);
                return;
            }
        }
        for (String prefix : AppURLs.PREFIX_WHITELIST) {
            if (matcher.match(prefix, uri)) {
                log.info("🎫 PREFIX 통과 : {}", uri);
                filterChain.doFilter(request, response);
                return;
            }
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 1) 인증 정보가 없거나, 폼 로그인 사용자면 필터 통과
        if (auth == null
                || !auth.isAuthenticated()
                || auth instanceof UsernamePasswordAuthenticationToken) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) OAuth2 로그인 사용자만 추가 가입 절차 검사
        if (auth instanceof OAuth2AuthenticationToken) {
            // 예외 URI: 추가정보 입력 페이지, 로그아웃
            if (uri.equals("/auth/joinExtra") || uri.startsWith("/logout")) {
                log.info("/auth/joinExtra 리다이렉트 시도");
                filterChain.doFilter(request, response);
                return;
            }

            // 가입 완료 여부 조회
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) auth.getPrincipal();
            String providerId = oauthUser.getName();
            memberRepository.findByProviderId(providerId).ifPresent(member -> {
                boolean signupDone = Boolean.TRUE.equals(member.getIsSignupCompleted());
                boolean active     = Boolean.TRUE.equals(member.getIsActive());
                if (!active && !signupDone) {
                    try {
                        log.info("/auth/joinExtra 리다이렉트 시도");
                        response.sendRedirect("/auth/joinExtra");

                    } catch (IOException e) {
                        log.error("Redirect to /auth/joinExtra failed", e);
                    }
                    return;
                }
            });
        }

        // 최종적으로 나머지 요청도 필터 체인 계속 실행
        filterChain.doFilter(request, response);
    }
}
