package com.jsj.socialLoginSession.dto.request;

import com.jsj.socialLoginSession.util.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestMemberProfile {

    private Long id;
    private String username;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String password;
    private String provider;

}
