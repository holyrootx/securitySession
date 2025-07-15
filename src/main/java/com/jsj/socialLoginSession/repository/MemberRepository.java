package com.jsj.socialLoginSession.repository;

import com.jsj.socialLoginSession.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
