package com.proj.animore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

	/**
	 * 회원가입유형선택
	 * @return
	 */
	@GetMapping("/join")
	public String joinSelectMtype() {
		return "member/joinSelectMtype";
	}

	/**
	 * 회원가입양식
	 * @param mtype
	 * @param model
	 * @return
	 */
	@GetMapping("/join/{mtype}")
	public String joinForm(
			@PathVariable("mtype") String mtype, Model model) {
		
		//회원유형 선택화면에서 회원유형값 받아서 조인폼으로 넘겨야됨.
		//조인폼에서 받은 회원유형 값으로 보여줄 항목, 안보여줄 항목 선택되도록
		
		if(mtype.equals("일반"))	mtype="N";
		if(mtype.equals("특수"))	mtype="S";
		
		model.addAttribute("mtype", mtype);
		return "/member/joinForm";
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
