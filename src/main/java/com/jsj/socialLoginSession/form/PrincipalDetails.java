package com.jsj.socialLoginSession.form;

import com.jsj.socialLoginSession.entity.Member;
import com.jsj.socialLoginSession.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private Member member;
    PrincipalDetails(Member member){
        this.member = member;
    }

    public Long getId(){
        return member.getId();
    }
    public String getPhoneNumber(){
        return member.getPhoneNumber();
    }
    public String getProvider(){
        return member.getProvider();
    }
    public String getProviderId(){
        return member.getProviderId();
    }
    public String getNickname(){
        return member.getNickname();
    }
    public String getName(){
        return member.getName();
    }

    public Boolean getIsActive(){
        return member.getIsActive();
    }
    public Boolean getIsSignupCompleted(){
        return member.getIsSignupCompleted();
    }

    public Member getMember(){
        return this.member;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = member.getRole();
        List<? extends GrantedAuthority> grantedAuthorityDefaults = List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.name();
            }
        });
        return grantedAuthorityDefaults;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
