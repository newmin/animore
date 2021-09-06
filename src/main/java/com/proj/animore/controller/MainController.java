package com.proj.animore.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proj.animore.common.Paginator;
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
	
  private static final Integer POSTS_PER_PAGE = 10;
  private static final Integer PAGES_PER_BLOCK = 5;

	// (카테고리별)업체목록 조회
	@GetMapping("/{bcategory}")
	public String list(@PathVariable String bcategory, HttpServletRequest request, Model model,
										@RequestParam(value = "page", defaultValue = "1") Integer page) {

		model.addAttribute("businessLoadDTO", new BusinessLoadDTO());

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

		
		// 페이지네이션
    try {
        Paginator paginator = new Paginator(PAGES_PER_BLOCK, POSTS_PER_PAGE, businessSVC.busiList(bcategory).count());
        Map<String, Object> pageInfo = paginator.getFixedBlock(page);
        model.addAttribute("pageInfo", pageInfo);
    } catch(IllegalStateException e) {
        model.addAttribute("pageInfo", null);
        System.err.println(e);
    }
		
		
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
