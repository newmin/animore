package com.proj.animore.controller;

import javax.servlet.http.HttpServletRequest;
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
   public String joinForm(@ModelAttribute JoinMemberForm joinMemberForm,
         HttpServletRequest request,
         @PathVariable("mtype") String mtype, Model model) {
      
      //회원유형 선택화면에서 회원유형값 받아서 조인폼으로 넘겨야됨.
      //조인폼에서 받은 회원유형 값으로 보여줄 항목, 안보여줄 항목 선택되도록
      
      if(mtype.equals("normal"))   mtype="N";
      if(mtype.equals("special"))   mtype="S";
      
      
      model.addAttribute("mtype", mtype);
      return "member/joinForm";
   }
   
//   @PostMapping("/join/N")
//   public String join1(MemberDTO memberDTO) {
//	      memberSVC.joinMember(memberDTO);
//	      return "redirect:/";
//	   }
   
   @PostMapping("/join/N")
   public String join(@Valid
		   			@ModelAttribute JoinMemberForm joinMemberForm, 
		   				MemberDTO memberDTO ,
		   				BindingResult bindingResult) {
	   if(!joinMemberForm.getPw().equals(joinMemberForm.getPw2())) {
		   bindingResult.rejectValue("pw2","pw2","비밀번호가 일치하지 않습니다.");
	   }
	   if(bindingResult.hasErrors()) {
			log.info("errors={}",bindingResult);
			return "member/joinForm";
		}
	   memberSVC.joinMember(memberDTO);
	   return "redirect:/";
   }
   
   @PostMapping("/join/S")
   public String join2(MemberDTO memberDTO, BusinessDTO businessDTO) {
      memberSVC.joinMember(memberDTO, businessDTO);
      return "redirect:/";
   }
   
   @GetMapping("/{id}")
   public String mypage() {
      return "mypage/mypage";
   }
}