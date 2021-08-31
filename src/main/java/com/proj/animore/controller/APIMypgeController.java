package com.proj.animore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.dto.BoardReqDTO;
import com.proj.animore.dto.ReviewReq;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.svc.BoardSVC;
import com.proj.animore.svc.ReviewSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class APIMypgeController {

	private final ReviewSVC reviewSVC;
	private final BoardSVC boardSVC;
	
	//내 리뷰 조회
	@GetMapping("/review")
	public Result myReview(HttpServletRequest request) {
		Result result;
		HttpSession session = request.getSession(false);
		if(session == null) {
			result = new Result("01","로그인후 내정보 페이지를 이용하실 수 있습니다.",null);
			return result;
		}
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		
		List<ReviewReq> list = reviewSVC.myReview(id);
		result = new Result("00","성공",list);
		return result;
	}
	
//내글불러오기
	@GetMapping("/mypost")
	public Result myPost(HttpServletRequest request) {
		Result result = new Result();
		HttpSession session = request.getSession(false);
		if(session == null) {
			result = new Result("01","로그인후 내정보 페이지를 이용하실 수 있습니다.",session);
			return result;
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();

		List<BoardReqDTO> list = boardSVC.findBoardById(id);
		//model.addAttribute("post",list);


		if (list.size() == 0) {
			result.setRtcd("01");
			result.setRtmsg("본인글이 존재하지않습니다.");
		} else {
			result.setRtcd("00");
			result.setRtmsg("성공");
			result.setData(list);
		}

		log.info("result:{}",result);
		return result;

	}
	
}
