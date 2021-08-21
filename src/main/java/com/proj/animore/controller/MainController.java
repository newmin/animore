package com.proj.animore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.proj.animore.dto.BusinessLoadDTO;
import com.proj.animore.svc.BusinessSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class MainController {

	private final BusinessSVC businessSVC;
	
	@GetMapping("/{bcategory}")
	public String list(@PathVariable String bcategory,Model model){
		List<BusinessLoadDTO> list = businessSVC.busiList(bcategory);
		model.addAttribute("busiList", list);
		return "map/busiList";
	}

	@GetMapping("/zx")
	public String tempList() {
		return "map/busiListTemp";
	}
	@GetMapping("/zxc")
	public String inquire() {
		return "map/inquireBusiDetail";
	}
	
}
