package com.jsj.socialLoginSession.oauth;

import com.jsj.socialLoginSession.dto.request.OAuthJoinRequest;
import com.jsj.socialLoginSession.entity.Member;
import com.jsj.socialLoginSession.repository.MemberRepository;
import com.jsj.socialLoginSession.util.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Spring Security의 oauth2Login() 설정에서 userInfoEndpoint().userService(...)
 * 이 등록되어 있을 때 OAuth2 콜백이 서비스로 들어오면 실행
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final List<OAuth2UserInfoFactory> userInfoFactories;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String email = null;
        try {

            // "google", "naver", "kakao"
            String provider = userRequest.getClientRegistration().getRegistrationId();

            // provider를 하나씩 가져와서 검사 후 해당하는 factory를 생성한다.
            OAuth2UserInfoFactory factory = userInfoFactories.stream()
                    .filter(f -> f.supports(provider))
                    .findFirst()
                    .orElseThrow(() ->
                            new OAuth2AuthenticationException("Unsupported provider: " + provider)
                    );

            OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

            OAuth2UserInfo oAuth2UserInfo = factory.create(oauth2User.getAttributes());

            Optional<Member> check = memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

            OAuthJoinRequest oAuthJoinRequest = new OAuthJoinRequest(oAuth2UserInfo);



            if (check.isEmpty()){
                Member member = oAuthJoinRequest.RequestToEntity();
                memberRepository.save(member);
            }

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_MEMBER.name()));

            return new DefaultOAuth2User(grantedAuthorities, oAuth2UserInfo.getAttributes(), oAuth2UserInfo.getProviderIdKey());

        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }

    }

}


