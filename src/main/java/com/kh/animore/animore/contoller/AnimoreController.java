package com.kh.animore.animore.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AnimoreController {
	
	@GetMapping("/main")
	public String home() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "joinForm";
	}
	@GetMapping("/mypage")
	public String mypage() {
		return "screen/mypage";
	}
	
	
}
