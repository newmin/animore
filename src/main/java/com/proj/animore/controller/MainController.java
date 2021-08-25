package com.proj.animore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proj.animore.dto.BusinessLoadDTO;
import com.proj.animore.dto.ReviewReq;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.ReviewForm;
import com.proj.animore.svc.BusinessSVC;
import com.proj.animore.svc.ReviewSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
//@RequestMapping("/company")
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
//	@GetMapping("/{bcategory}/{bnum}")
	@GetMapping("/inquire/{bnum}")
	public String inquire(
//			@PathVariable String bcategory,
			@PathVariable Integer bnum,
			HttpServletRequest request,
												Model model) {
		BusinessLoadDTO businessLoadDTO = businessSVC.findBusiByBnum(bnum);
		model.addAttribute("busi",businessLoadDTO);
		
//		HttpSession session = request.getSession(false);
//		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
//		
//		List<ReviewReq> rvlist = null;
//		if(loginMember!=null) rvlist = reviewSVC.allReview(bnum);
		//비로그인 상태일 경우, 리뷰는 출력안되도록? 출력은 하되 뷰에서 가리도록?
		
		List<ReviewReq> rvlist = reviewSVC.allReview(bnum);
		model.addAttribute("review", rvlist);
		
		return "map/inquireBusiDetail";
	}
	
	@PostMapping("/inquire/{bnum}")
	public String addReview(@PathVariable Integer bnum, @ModelAttribute ReviewForm reviewForm, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) return "member/login";
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();

		log.info(reviewForm.toString());
		
		reviewSVC.registReview(bnum, id, reviewForm);
		return "redirect:/inquire/{bnum}";
	}
	
	
}
