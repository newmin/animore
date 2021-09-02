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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.proj.animore.dto.FavoriteDTO;
import com.proj.animore.form.LoginMember;
import com.proj.animore.svc.FavoriteSVC;
import com.proj.animore.svc.MemberSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/mypage")
@RequiredArgsConstructor
@SessionAttributes("id")
public class MyPageController {
	private final FavoriteSVC favoriteSVC;
	private final MemberSVC memberSVC;
	
	@GetMapping("/mypageFavorites")
	public String mypage(HttpServletRequest request,
			Model model) {
				
		HttpSession session = request.getSession(false);
 		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
 		String id = loginMember.getId();
 		log.info(id);

 		List<FavoriteDTO> favoritelist = favoriteSVC.favoriteList(id);

 		model.addAttribute("FavoriteDTO",favoritelist);

		return "mypage/mypageFavorites";
	}

	//회원탈퇴처리
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
//			return "mypage/memberOutForm";
			return null;
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
//			return "mypage/memberOutForm";
			return null;
		}
		
		session.invalidate();
		
		return "redirect:/";
	}
}
