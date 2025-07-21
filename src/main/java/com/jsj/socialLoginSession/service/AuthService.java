package com.jsj.socialLoginSession.service;

import com.jsj.socialLoginSession.dto.request.JoinExtraRequest;
import com.jsj.socialLoginSession.entity.Member;
import com.jsj.socialLoginSession.exception.MemberAlreadyExistsException;
import com.jsj.socialLoginSession.exception.MemberEmailIsConplictException;
import com.jsj.socialLoginSession.exception.MemberNotFoundException;
import com.jsj.socialLoginSession.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    public void joinMemberBySocial(JoinExtraRequest request) throws
            MemberNotFoundException,
            MemberAlreadyExistsException,
            MemberEmailIsConplictException {

        String email = request.getEmail();

        Optional<Member> byEmail = memberRepository.findByEmail(email);

        if (byEmail.isPresent() && !byEmail.get().getProvider().equals("local")){
            Member member = byEmail.get();
            member.setName(request.getName());
            member.setNickname(request.getNickname());
            member.setPhoneNumber(request.getPhoneNumber());
            member.setIsActive(true);
            member.setIsSignupCompleted(true);

        } else if(byEmail.isPresent() && byEmail.get().getProvider().equals("local")){
            throw new MemberAlreadyExistsException("이미 회원가입된 이메일이 존재합니다.");
        } else if(byEmail.isEmpty()){
            throw new MemberNotFoundException("소셜 로그인을 다시 진행해 주세요");
        } else {
            String provider = byEmail.get().getProvider();
            throw new MemberEmailIsConplictException(provider + "로 이미 가입하셨습니다. \n해당 소셜 로그인으로 진행해 주세요");
        }

    }

}
