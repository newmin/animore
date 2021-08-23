package com.proj.animore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		
		model.addAttribute("businessLoadDTO",new BusinessLoadDTO()); 
		List<BusinessLoadDTO> list = businessSVC.busiList(bcategory);
		model.addAttribute("busiList", list);
		return "map/busiList";
	}

	@GetMapping("/inquire/{bnum}")
	public String inquire(@PathVariable Integer bnum,
												Model model) {
		BusinessLoadDTO businessLoadDTO = businessSVC.findBusiByBnum(bnum);
		model.addAttribute("busi",businessLoadDTO);
		
		log.info(businessLoadDTO.toString());
		
		return "map/inquireBusiDetail";
	}
	
	
}
