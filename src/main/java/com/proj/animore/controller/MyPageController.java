package com.proj.animore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proj.animore.dto.MypageReplyRes;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.svc.MemberSVC;
import com.proj.animore.svc.MypageSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
	private final MemberSVC memberSVC;
	private final MypageSVC mypageSVC;
	
	@GetMapping("/mypageFavorites")
	public String mypage() {
		return "mypage/mypageFavorites";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/mypageReply")
	public String mypageReply(
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		log.info("loginMember:{}",loginMember);
		
		List<MypageReplyRes> list = mypageSVC.mypageReply(loginMember.getId());
		
		model.addAttribute("mypagereply", list);
		
//		list.forEach(ele->{
//			log.info("ele:{}",ele);
//		});
		return "mypage/mypageReply";
	}
	
	
	@ResponseBody
	@GetMapping("/mypageDel")
	public Result mypageDel() {
		
		Result result;
		
		StringBuffer html = new StringBuffer();
		html.append("<h3 class=\"mypage_content_title\">회원탈퇴</h3>");
		html.append("<hr>");
		html.append("<div class=\"mypage_content\">");
		html.append("  <form action=\"/mypage/mypageDel\" th:method=\"delete\" class=\"findId\">");
		html.append("    <h1 class=\"findId__title\"></h1>");
		html.append("    <!-- <p class=\"login__errormsg\" th:errors=\"*{global}\"></p> -->");
		html.append("    <div class=\"findId__form\">");
		html.append("      <span class=\"findId__text\">비밀번호 확인</span>");
		html.append("      <input class=\"findId__input\" type=\"text\" name=\"pw\">");
		html.append("    </div>");
		html.append("    <button class=\"findId__btn\" type=\"submit\">회원탈퇴</button>");
		html.append("  </form>");
		html.append("</div>");
		
		result = new Result("00","OK",html);
		
		return result;
	}
//	@GetMapping("/mypageDel")
//	public String mypageDel() {
//		
//		
//		
//		return "mypage/mypageDel";
//	}
	
	@DeleteMapping("/mypageDel")
	public String mypageDel(
			@RequestParam String pw,
			HttpServletRequest request,
			Model model) {
		log.info("회원탈퇴");
		
		Map<String, String> errors = new HashMap<>();
		
		if(pw == null || pw.trim().length() == 0) {
			errors.put("pw", "비밀번호를 입력하세요");
			model.addAttribute("errors", errors);
			return "mypage/memberOutForm";
		}
		
		HttpSession session = request.getSession(false);
		if(session == null) return "redirect:/login";
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		//회원존재유무 확인
		if(memberSVC.isMemember(loginMember.getId(), pw)) {
			//탈퇴
			memberSVC.outMember(loginMember.getId(), pw);
		}else {
			errors.put("global", "비밀번호가 잘못되었습니다.");
			model.addAttribute("errors", errors);
		}
		
		if(!errors.isEmpty()) {
			return "mypage/memberOutForm";
		}
		
		session.invalidate();
		
		return "redirect:/";
	}
}
