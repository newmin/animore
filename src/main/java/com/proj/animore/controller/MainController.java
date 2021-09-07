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
import com.proj.animore.dto.FavoriteReq;
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
// @RequestMapping("/company")
public class MainController {

	private final ReviewSVC reviewSVC;
	private final BusinessSVC businessSVC;
	private final FavoriteSVC favoriteSVC;

	// (카테고리별)업체목록 조회
	@GetMapping("/{bcategory}")
	public String list(@PathVariable String bcategory, HttpServletRequest request, Model model) {

		model.addAttribute("businessLoadDTO", new BusinessLoadDTO());
		//로그인회원은 즐겨찾기 상단고정한 목록 메소드 
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
			String id = loginMember.getId();

			List<BusinessLoadDTO> list = businessSVC.busiListForMember(bcategory, id);
			model.addAttribute("busiList", list);
		} else {
			List<BusinessLoadDTO> list = businessSVC.busiList(bcategory);
			model.addAttribute("busiList", list);
		}

		return "map/busiList";
	}
	
	//업체검색어 목록조회
	@GetMapping("/")
	public String listBySearch(@RequestParam String search,Model model) {
		
		model.addAttribute("businessLoadDTO", new BusinessLoadDTO());
		
//		List<BusinessLoadDTO> list = businessSVC.검색어조회메소드(search);
//		model.addAttribute("busiList",list);
		
		return "map/busiList";
	}
	

	// 업체조회(상세보기) + 리뷰조회
	// @GetMapping("/{bcategory}/{bnum}")
	@GetMapping("/inquire/{bnum}")
	public String inquire(
			// @PathVariable String bcategory,
			@PathVariable int bnum, HttpServletRequest request, Model model) {
		BusinessLoadDTO businessLoadDTO = businessSVC.findBusiByBnum(bnum);
		model.addAttribute("busi", businessLoadDTO);

		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
			String id = loginMember.getId();

			FavoriteReq isFavor = favoriteSVC.isFavorite(bnum, id);
			model.addAttribute("favor", isFavor);
		}

		List<ReviewReq> rvlist = reviewSVC.allReview(bnum);
		model.addAttribute("review", rvlist);

		return "map/inquireBusiDetail";
	}

	// 즐겨찾기 등록
	@GetMapping("/favor/{bnum}")
	@ResponseBody
	public Result addFavorite(HttpServletRequest request, @PathVariable Integer bnum) {
		Result result;
		HttpSession session = request.getSession(false);
		if (session == null) {
			result = new Result("01", "로그인 후 사용할 수 있어요!", null);
			return result;
		}
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		String id = loginMember.getId();

		log.info(id);

		Integer cnt = favoriteSVC.isFavorite(bnum, id).getCount();
		if (cnt == 0) {
			favoriteSVC.addFavorite(bnum, id);
			result = new Result<String>("00", "성공", "등록");
		} else {
			favoriteSVC.deleteFavorite(bnum, id);
			result = new Result<String>("00", "성공", "해제");
		}

		return result;
	}
	// //즐겨찾기 등록
	// @GetMapping("/favor/{bnum}")
	//// @ResponseBody
	// public Result addFavorite(HttpServletRequest request,
	// @PathVariable Integer bnum) {
	// Result result;
	// HttpSession session = request.getSession(false);
	// if(session==null) {
	// result = new Result("01","로그인 후 사용할 수 있어요!",null);
	// return result;
	// }
	// LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
	// String id = loginMember.getId();
	//
	// favoriteSVC.addFavorite(bnum, id);
	// result = new Result("00","성공",null);
	//
	// return result;
	// }

	// 즐겨찾기 삭제
	// @DeleteMapping("/favor/{bnum}")
	// @ResponseBody
	// public Result<String> delFavorite(HttpServletRequest request,
	// @PathVariable Integer bnum) {
	// Result<String> result;
	// HttpSession session = request.getSession(false);
	// if(session==null) {
	// result = new Result<String>("01","로그인 후 사용할 수 있어요!","삭제");
	// return result;
	// }
	// LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
	// String id = loginMember.getId();
	//
	// favoriteSVC.deleteFavorite(bnum, id);
	// result = new Result<String>("00","성공","삭제");
	//
	// return result;
	// }

}
