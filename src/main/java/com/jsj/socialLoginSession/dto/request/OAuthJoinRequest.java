package com.jsj.socialLoginSession.dto.request;

import com.jsj.socialLoginSession.entity.Member;
import com.jsj.socialLoginSession.oauth.OAuth2UserInfo;
import com.jsj.socialLoginSession.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthJoinRequest {

    private String name;
    private String email;
    private String role;
    private String provider;
    private String providerId;
    private Boolean isActive = false;
    private Boolean isSignupCompleted = false;

    public OAuthJoinRequest(OAuth2UserInfo oAuth2UserInfo){
        this.name = oAuth2UserInfo.getName();
        this.email = oAuth2UserInfo.getEmail();
        this.role = Role.ROLE_MEMBER.name();
        this.provider = oAuth2UserInfo.getProvider();
        this.providerId = oAuth2UserInfo.getProviderId();
    }

    public Member RequestToEntity() {
        return Member.builder()
                .name(this.getName())
                .email(this.getEmail())
                .role(Role.valueOf(this.getRole()))
                .providerId(this.getProviderId())
                .provider(this.getProvider())
                .build();
    }
}
