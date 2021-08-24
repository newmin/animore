package com.proj.animore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.proj.animore.dto.BusinessLoadDTO;
import com.proj.animore.dto.ReviewReq;
import com.proj.animore.svc.BusinessSVC;
import com.proj.animore.svc.ReviewSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class MainController {

	private final ReviewSVC reviewSVC;
	private final BusinessSVC businessSVC;
	
//	(카테고리별)업체목록 조회
	@GetMapping("/{bcategory}")
	public String list(@PathVariable String bcategory,Model model){
		
		model.addAttribute("businessLoadDTO",new BusinessLoadDTO()); 
		List<BusinessLoadDTO> list = businessSVC.busiList(bcategory);
		model.addAttribute("busiList", list);
		
		return "map/busiList";
	}

	//업체조회(상세보기)
	@GetMapping("/inqu/{bnum}")
//	@GetMapping("/{bcategory}/{bnum}")
	public String inquire(
												@PathVariable String bcategory,
												@PathVariable Integer bnum,
												Model model) {
		BusinessLoadDTO businessLoadDTO = businessSVC.findBusiByBnum(bnum);
		model.addAttribute("busi",businessLoadDTO);
		
		List<ReviewReq> rvlist = reviewSVC.allReview(bnum);
		model.addAttribute("review", rvlist);
		
		log.info(businessLoadDTO.toString());
		log.info(rvlist.toString());
		
		return "map/inquireBusiDetail";
	}
	
	
}
