package com.proj.animore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proj.animore.dto.BoardReqDTO;
import com.proj.animore.svc.BoardSVC;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

	private final BoardSVC boardSVC;
	
	@GetMapping("")
	public String home(Model model) {
		
		 String bcategory = "Q";
		 List<BoardReqDTO> list = boardSVC.list(bcategory);
     model.addAttribute("post",list);
     
		return "index";
	}
}
