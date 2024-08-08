package com.itwill.springboot5.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot5.domain.Member;
import com.itwill.springboot5.domain.MemberRole;
import com.itwill.springboot5.dto.MemberSignUpDto;
import com.itwill.springboot5.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepo;

    @Transactional // 여러개의 쿼리 작업들이 순서대로 필요한 경우 써야한다. 한줄 정도는 생략 가능. 
    public Member create(MemberSignUpDto dto) {
        log.info("create(dto={})", dto);

        Member member = memberRepo.save(dto.toEntity(passwordEncoder).addRole(MemberRole.USER));
        // save() -> (1) insert into members, (2) insert into member_roles

        return member;
    }
    
}
