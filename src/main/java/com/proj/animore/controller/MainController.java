package com.proj.animore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proj.animore.dto.BusinessLoadDTO;
import com.proj.animore.dto.ReviewReq;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.svc.BusinessSVC;
import com.proj.animore.svc.FavoriteSVC;
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
	private final FavoriteSVC favoriteSVC;
	
//	(카테고리별)업체목록 조회
	@GetMapping("/{bcategory}")
	public String list(@PathVariable String bcategory,Model model){
		
		model.addAttribute("businessLoadDTO",new BusinessLoadDTO()); 
		List<BusinessLoadDTO> list = businessSVC.busiList(bcategory);
		model.addAttribute("busiList", list);
		
		return "map/busiList";
	}
	
	//업체조회(상세보기) + 리뷰조회
//	@GetMapping("/{bcategory}/{bnum}")
	@GetMapping("/inquire/{bnum}")
	public String inquire(
//			@PathVariable String bcategory,
			@PathVariable int bnum,
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
	
	
	
	//즐겨찾기 등록
	@GetMapping("/addFavor")
	@ResponseBody
	public Result addFavorite(HttpServletRequest request,
													@RequestParam Integer bnum) {
		Result result;
		HttpSession session = request.getSession(false);
		if(session==null) {
			result = new Result("01","로그인 후 사용할 수 있어요!",null);
			return result;
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("LoginMember");
		String id = loginMember.getId();
		
		favoriteSVC.addFavorite(bnum, id);
		result = new Result("00","성공",null);
		
		return result;
	}
	
	//즐겨찾기 삭제
//즐겨찾기 등록
	@DeleteMapping("/delFavor")
	@ResponseBody
	public Result delFavorite(HttpServletRequest request,
													@RequestParam Integer bnum) {
		Result result;
		HttpSession session = request.getSession(false);
		if(session==null) {
			result = new Result("01","로그인 후 사용할 수 있어요!",null);
			return result;
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("LoginMember");
		String id = loginMember.getId();
		
		favoriteSVC.deleteFavorite(bnum, id);
		result = new Result("00","성공",null);
		
		return result;
	}
	
}
