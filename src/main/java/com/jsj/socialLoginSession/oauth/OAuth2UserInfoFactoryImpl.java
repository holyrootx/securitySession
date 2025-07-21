package com.jsj.socialLoginSession.oauth;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuth2UserInfoFactoryImpl implements OAuth2UserInfoFactory {

    @Override
    public boolean supports(String registrationId) {
        if ("kakao".equals(registrationId)){
            return true;
        } else if ("google".equals(registrationId)){
            return true;
        } else if ("naver".equals(registrationId)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public OAuth2UserInfo create(Map<String, Object> attributes) {
        return null;
    }

    @Override
    public OAuth2UserInfo create(Map<String, Object> attributes, String provider) {

        if ("kakao".equals(provider)){
            return new KakaoUserInfo(attributes);
        } else if ("google".equals(provider)){
            return new GoogleUserInfo(attributes);
        } else if ("naver".equals(provider)){
            return new NaverUserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        }
    }


}
