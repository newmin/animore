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

import com.proj.animore.common.file.FileStore;
import com.proj.animore.dto.business.BusinessLoadDTO;
import com.proj.animore.dto.business.FavoriteReq;
import com.proj.animore.dto.business.ReviewReq;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.svc.business.BusinessSVC;
import com.proj.animore.svc.business.FavoriteSVC;
import com.proj.animore.svc.business.ReviewSVC;

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
	private final FileStore fileStore;		
	
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
	@GetMapping("/search")
	public String listBySearch(@RequestParam String search,Model model) {
		
		model.addAttribute("businessLoadDTO", new BusinessLoadDTO());
		
		List<BusinessLoadDTO> list = businessSVC.busiListBySearch(search);
		model.addAttribute("busiList",list);
		
		return "map/busiList";
	}
	

	// 업체조회(상세보기) + 리뷰조회
	@GetMapping("/{bcategory}/store/{bnum}")
	public String inquire(
			@PathVariable String bcategory,
			@PathVariable int bnum, HttpServletRequest request, Model model) {
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/business/");
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

	// 즐겨찾기 등록ㆍ삭제
	@GetMapping("/favor/{bnum}")
	@ResponseBody
	public Result addFavorite(HttpServletRequest request, @PathVariable Integer bnum) {
		Result result;
		HttpSession session = request.getSession(false);
		if (session == null) {
			result = new Result("01", "로그인 후 사용할 수 있어요!", null);
			return result;
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
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
	
}
