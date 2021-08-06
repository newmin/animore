package com.proj.animore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping("/join")
	public String joinForm() {
		return "member/joinForm";
	}
	
	@PostMapping("/join")
	public String join() {
//		TODO DTO받아서 SVC실행
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String mypage() {
		return "mypage/mypage";
	}
}
