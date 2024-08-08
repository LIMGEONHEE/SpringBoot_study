package com.itwill.springboot5.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot5.domain.Member;
import com.itwill.springboot5.dto.MemberSignUpDto;
import com.itwill.springboot5.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberSvc;
    
	@GetMapping("/signin") 
	// SecurityConfig.securityFilterChain 메서드에서 formLogin() 아규먼트.
	public void signIn() {
		log.info("GET signIn()");
	}

	@GetMapping("/signup")
	public void signUp() {
		log.info("GET signUp()");
	}

	@PostMapping("/signup") // 오버로딩, 아규먼트가 다르기 때문에 가능.
	public String signUp(MemberSignUpDto dto) {
		log.info("POST signUp(dto={})", dto);

		// 서비스 계층의 메서드를 호출해서 회원가입 정보들을 DB에 insert.
		Member member = memberSvc.create(dto);
		log.info("회원가입: {}", member);

		// 회원가입 성공하면 로그인 페이지로 이동
		return "redirect:/member/signin";
	}
}
