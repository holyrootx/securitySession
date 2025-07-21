package com.jsj.socialLoginSession.util;

import lombok.Getter;

import java.util.Arrays;

/**
 * 인증 및 권한별 허용/차단 URL 상수 정의
 */
@Getter
public class AppURLs {

    /**
     * Security에서 사용
     */
    public static final String[] PUBLIC_URLS = {
            // TODO: 인증 자체가 필요 없는 url을 정확하게 작성
            "/",
            "/main",
            "/auth/loginForm",
            "/auth/joinForm",
            "/auth/login",
            "/auth/join",
            "/auth/joinExtra",
            "/auth/validate/extra",
            "/auth/logout",
            "/favicon.ico",
            "/firebase-messaging-sw.js"
    };


    /**
     * 정적 리소스(prefix) 화이트리스트 URL
     *
     * Security, JwtAuthenticationFilter에서 사용
     */
    public static final String[] PREFIX_WHITELIST = {
            // TODO: 인증 자체가 필요 없는 url 접두사를 작성
            "/css/**",
            "/js/**",
            "/images/**",
            "/.well-known/**",
    };

    /**
     * 나머지 : SecuirtyConfig에서 사용
     */
    public static final String[] MEMBER_URLS = {
            // TODO: Member 전용 엔드포인트 명시
    };
    public static final String[] ADMIN_URLS = {
            // TODO: Admin 전용 엔드포인트 명시
    };

    public static final String[] MEMBER_URLS_PREFIX = {
            // TODO: Member 전용 PREFIX 작성
            "/member/**"
    };
    public static final String[] ADMIN_URLS_PREFIX = {
            // TODO: Admin 전용 PREFIX 작성
            "/admin/**"
    };


    /** 2중 권한 조합: Admin + Manager */
    public static final String[] ADMIN_MEMBER_URLS = {
            // TODO: Admin, Manager 공통 엔드포인트 명시
    };



    public static String[] getCombineURL(String[]... arrays) {
        return Arrays.stream(arrays)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }

}

