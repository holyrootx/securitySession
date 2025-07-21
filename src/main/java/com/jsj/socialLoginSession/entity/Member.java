package com.jsj.socialLoginSession.entity;

import com.jsj.socialLoginSession.util.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;
    private String password;

    private String phoneNumber;
    private String name;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;
    private String providerId;

    private Boolean isActive = false;
    private Boolean isSignupCompleted = false;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.isActive = false;
        this.isSignupCompleted = false;

    }

}

