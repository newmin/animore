package com.proj.animore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proj.animore.svc.BoardSVC;
import com.proj.animore.svc.RboardSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
	
	@GetMapping("/mypageFavorites")
	public String mypage() {
		return "mypage/mypageFavorites";
	}
}


