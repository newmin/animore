package com.proj.animore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.form.ChangePwForm;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.FindPwForm;
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
	public String findId(@ModelAttribute FindIdForm findIdForm, Model model) {
		List<String> ids = memberSVC.findId(findIdForm);
		model.addAttribute("ids",ids);
		return "member/findedIdResult";
	}
	
	
	/**
	 * 비밀번호찾기양식
	 * @return
	 */
	@GetMapping("/findPW")
	public String findPWForm(@ModelAttribute FindPwForm findPwForm) {
		return "member/findPWForm";
	}
	
	/**
	 * 비밀번호찾기 - 아이디,이름,이메일 입력받음
	 * @param findPwForm
	 * @return
	 */
	@PostMapping("/findPW")
	public String findPW(
			@ModelAttribute FindPwForm findPwForm,
			RedirectAttributes redirectAttributes) {
		
		ChangePwForm changePwForm = memberSVC.findPw(findPwForm);
		
		//정보가 없으면
		if(changePwForm == null) {
			return "member/findPWForm";
		}
		
		//정보가 DB와 일치할 경우
		redirectAttributes.addAttribute("id", changePwForm.getId());
		log.info(changePwForm.getId());
		return "redirect:/member/findPW/{id}";	//비밀번호변경양식
	}
	
	/**
	 * 비밀번호변경양식
	 * @param changePWForm
	 * @return
	 */
	@GetMapping("/findPW/{id}")
	public String changePWForm(
			@ModelAttribute ChangePwForm changePWForm,
			@PathVariable String id) {
		
		
		return "member/changePWForm";
	}

	/**
	 * 비밀번호변경처리
	 * @param changePWForm
	 * @param id
	 * @return
	 */
	@PatchMapping("/findPW/{id}")
	public String changePW(
			@PathVariable String id,
			@Valid @ModelAttribute ChangePwForm changePWForm,
			BindingResult bindingResult) {
		
		if(!changePWForm.getPw().equals(changePWForm.getPwChk())) {
			bindingResult.reject("pwChk","새 비밀번호 확인이 일치하지 않습니다.");	//TODO 비밀번호 안맞을때 메세지 뭐라고 할까
			return "member/changePWForm";
		}
		
		int result = memberSVC.changePW(changePWForm);
		if(result == 0) {
			return "member/changePWForm";
		}
		
		return "redirect:/member/changePWSuccess";
	}
	@GetMapping("/changePWSuccess")
	public String changePWSuccess() {
		
		return "/member/changePWSuccess";
	}


	
}