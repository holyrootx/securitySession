package com.jsj.socialLoginSession.dto;

import com.jsj.socialLoginSession.util.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String role;
    private String provider;
    private String providerId;

}

