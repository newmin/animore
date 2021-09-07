package com.proj.animore.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
		if(session == null || session.getAttribute("loginMember") == null) {
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
	// //리뷰1개 호출(리뷰수정폼)
	@GetMapping("/")
	public Result findReview(@RequestParam int rnum,
												  //  @RequestParam String id,
													 HttpServletRequest request
													 ) {
		Result result;											
		//로그인x
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loginMember")==null){
			result = new Result("01","로그인이 만료되었습니다.",null);
			return result;
		}												
		//아이디값 다른 경우
		// LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		// if(!id.equals(loginMember.getId())){
		// 	result = new Result("02","작성자의 아이디와 일치하지 않습니다.",null);
		// 	return result;
		// }

		ReviewReq reviewReq	= reviewSVC.findReview(rnum);
		result = new Result("00","성공",reviewReq);
		return result;
	}
	
	// //리뷰수정
	@PatchMapping("/")
	public Result modiReview(@RequestBody ReviewForm reviewForm, 
													 HttpServletRequest request) {
		Result result;
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("loginMember") == null) {
			result = new Result("01","로그인이 만료되었어요. 다시 로그인해주세요.",null);
			return result;
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();

		if(loginMember.getId() != reviewForm.getId()){
			result = new Result("01","해당 리뷰작성자와의 아이디가 일치하지 않습니다.",null);
			return result;
		}
		
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
														//  @RequestParam String rid,
														 HttpServletRequest request) {
		Result result;
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loginMember") == null) {
			result = new Result("01","로그인이 만료되었어요. 다시 로그인해주세요.",null);
			return result;
		}
		//로그인회원ID과 리뷰작성자ID가 일치하지 않을 경우
		//뷰에서 작성자와 일치할 경우에만 삭제버튼을 띄우지만 혹시나? never?
		// LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		// log.info("id.sess={}, id.req={}", loginMember.getId(), rid);

		// if(loginMember.getId() != rid){
		// 	result = new Result("01","해당 리뷰작성자와의 아이디가 일치하지 않습니다.",null);
		// 	return result;
		// }
		
		List<ReviewReq> list = reviewSVC.removeReview(bnum, rnum);
		result = new Result("00","성공",list);
		return result;
	}
	
	
}
