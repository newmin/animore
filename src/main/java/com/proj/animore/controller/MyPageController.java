package com.proj.animore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.proj.animore.dto.FavoriteDTO;
import com.proj.animore.form.FavoriteForm;
import com.proj.animore.form.LoginMember;
import com.proj.animore.svc.FavoriteSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/mypage")
@RequiredArgsConstructor

@SessionAttributes("id")
public class MyPageController {
	
	private final FavoriteSVC favoriteSVC;
	
	@GetMapping("/mypageFavorites")
	public String mypage(HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		log.info(id);
		
		List<FavoriteDTO> favoritelist = favoriteSVC.favoritelist(id);
		
		model.addAttribute("FavoriteDTO",favoritelist);
		
		return "mypage/mypageFavorites";
	}
}


