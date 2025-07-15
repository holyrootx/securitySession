package com.jsj.socialLoginSession.entity;

import com.jsj.socialLoginSession.util.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;
    private String password;

    private String phoneNumber;

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
    }


}

