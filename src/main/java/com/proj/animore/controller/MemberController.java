package com.proj.animore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.JoinMemberForm;
import com.proj.animore.svc.MemberSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberSVC memberSVC;

	/**
	 * 회원가입유형선택
	 * 
	 * @return
	 */
	@GetMapping("/join")
	public String joinSelectMtype(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		// 로그인한 상태(세션있음)로 회원가입유형선택 페이지 요청시 메인페이지로 보냄
		if (session != null)	return "redirect:/";
		// 로그인 안한 상태(세션없음)로 회원가입유형선택 페이지 요청해야 페이지로 이동
		return "member/joinSelectMtype";
	}

	/**
	 * 회원가입양식
	 * 
	 * @param mtype
	 * @param model
	 * @return
	 */
	@GetMapping("/join/{mtype}")
	public String joinForm(@ModelAttribute JoinMemberForm joinMemberForm, HttpServletRequest request,
			@PathVariable("mtype") String mtype, Model model) {
		HttpSession session = request.getSession(false);

		// 로그인한 상태(세션있음)로 회원가입양식 페이지 요청시 메인페이지로 보냄
		if (session != null)	return "redirect:/";

		// 회원유형 선택화면에서 회원유형값{mtype} 받아서 조인폼으로 넘겨주게 됨.
		// joinForm에서 th:if문을 사용하여
		// 회원유형값이 N이면 일반회원 가입양식,
		// 회원유형값이 S이면 특수회원 가입양식이 보여지도록 함.
		if (mtype.equals("normal"))		mtype = "N";
		if (mtype.equals("special"))	mtype = "S";

		model.addAttribute("mtype", mtype);
		return "member/joinForm";
	}

//   @PostMapping("/join/N")
//   public String join1(MemberDTO memberDTO) {
//	      memberSVC.joinMember(memberDTO);
//	      return "redirect:/";
//	   }

	@PostMapping("/join/N")
	public String join(@Valid @ModelAttribute JoinMemberForm joinMemberForm, MemberDTO memberDTO,
			BindingResult bindingResult) {
		if (!joinMemberForm.getPw().equals(joinMemberForm.getPw2())) {
			bindingResult.rejectValue("pw2", "pw2", "비밀번호가 일치하지 않습니다.");
		}
		if (bindingResult.hasErrors()) {
			log.info("errors={}", bindingResult);
			return "member/joinForm";
		}
		memberSVC.joinMember(memberDTO);
		return "redirect:/";
	}

	// TODO 전문가 회원가입
	@PostMapping("/join/S")
	public String join2(MemberDTO memberDTO, BusinessDTO businessDTO) {
		memberSVC.joinMember(memberDTO, businessDTO);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String mypage() {
		return "mypage/mypage";
	}
	
	/**
	 * 아이디찾기양식
	 * @return
	 */
	@GetMapping("/findId")
	public String findIdForm(@ModelAttribute FindIdForm findIdForm) {
		return "member/findIdForm";
	}
	//아이디찾기 처리
	@PostMapping("/findId")
	public String findId(@ModelAttribute FindIdForm findIdForm) {
		memberSVC.findId(findIdForm);
		log.info("아이디찾기 처리됨");
		return "redirect:/";
	}
	
	
	/**
	 * 비밀번호찾기양식
	 * @return
	 */
	@GetMapping("/findPW")
	public String findPW() {
		return "member/findPWForm";
	}
	
}