package com.jsj.socialLoginSession.oauth;



import com.jsj.socialLoginSession.dto.MemberDTO;
import com.jsj.socialLoginSession.dto.request.OAuthJoinRequest;
import com.jsj.socialLoginSession.entity.Member;
import com.jsj.socialLoginSession.repository.MemberRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.jsj.socialLoginSession.util.Role;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final List<OAuth2UserInfoFactory> userInfoFactories;
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException, OAuth2AuthenticationException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        log.info(" oauth Token : {}",String.valueOf(oauthToken));
        // ex : kakao, naver, google

        String provider = oauthToken.getAuthorizedClientRegistrationId();
        OAuth2User oauthUser = oauthToken.getPrincipal();

        Map<String, Object> attributes = oauthUser.getAttributes();

        OAuth2UserInfoFactory userInfoFactory = userInfoFactories.stream()
                .filter(f -> f.supports(provider))
                .findFirst()
                .orElseThrow(() -> new OAuth2AuthenticationException("Unsupported provider: " + provider));

        OAuth2UserInfo oAuth2UserInfo = userInfoFactory.create(attributes);

        OAuthJoinRequest oAuthJoinRequest = new OAuthJoinRequest(oAuth2UserInfo);

        Member member = memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId()).get();

        Boolean isActive = member.getIsActive();
        Boolean isSignupCompleted = member.getIsSignupCompleted();

        if (!isActive && !isSignupCompleted){
            request.getSession().setAttribute("email", member.getEmail());
            request.getSession().setAttribute("name", member.getName());
            response.sendRedirect("/auth/joinExtra");
        } else{
             response.sendRedirect("/");
        }


    }

}

