package com.proj.animore.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.dto.ReviewDTO;
import com.proj.animore.dto.ReviewReq;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.form.ReviewForm;
import com.proj.animore.svc.ReviewSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/inquire")
public class APIReviewController {
	
	private final ReviewSVC reviewSVC;

	//리뷰등록
	@PostMapping("/")
	public Result addReview(@RequestBody ReviewForm reviewForm, 
													HttpServletRequest request) {
		
		Result result;
		
		HttpSession session = request.getSession(false);
		//비로그인/로그인만료 상태라면?
		if(session == null) {
			result = new Result("01","로그인이 만료되었어요 다시 로그인해주세요.",null);
			return result;
		}
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();

		//리뷰작성폼→리뷰DTO
		ReviewDTO reviewDTO = new ReviewDTO();
		BeanUtils.copyProperties(reviewForm,reviewDTO);
		
		int bnum = reviewDTO.getBnum();
		
		List<ReviewReq> list = reviewSVC.registReview(bnum, id, reviewDTO);
		
		result = new Result("00","성공",list);
	  	return result;
	}
	
	//리뷰수정
	@PatchMapping("/")
	public Result modiReview(@RequestBody ReviewForm reviewForm, 
													 HttpServletRequest request) {
		Result result;
		HttpSession session = request.getSession(false);
		if(session==null) {
			result = new Result("01","로그인이 만료되었어요. 다시 로그인해주세요.",null);
			return result;
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		
		//리뷰작성폼→리뷰DTO
		ReviewDTO reviewDTO = new ReviewDTO();
		BeanUtils.copyProperties(reviewForm,reviewDTO);
		
		int bnum = reviewDTO.getBnum();
		
		List<ReviewReq> list = reviewSVC.updateReview(bnum, id, reviewDTO);
		result = new Result("00","성공",list);
		return result;
	}
	
	//리뷰삭제
	@DeleteMapping("/")
	public Result deleteReview(@RequestParam int bnum,
														 @RequestParam int rnum,
														 HttpServletRequest request) {
		Result result;
		HttpSession session = request.getSession(false);
		if(session == null) {
			result = new Result("01","로그인이 만료되었어요. 다시 로그인해주세요.",null);
			return result;
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		
		List<ReviewReq> list = reviewSVC.removeReview(bnum, rnum, id);
		result = new Result("00","성공",list);
		return result;
	}
	
	
}
