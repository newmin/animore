package com.proj.animore.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.dto.BoardReqDTO;
import com.proj.animore.dto.MypageReplyRes;
import com.proj.animore.dto.ReviewReq;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.svc.BoardSVC;
import com.proj.animore.svc.MypageSVC;
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
	private final MypageSVC mypageSVC;
	
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
	//내 댓글 조회
	@GetMapping("/mypageReply")
	public Result mypageReply(HttpServletRequest request) {
	
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		log.info("loginMember:{}",loginMember);
		
		List<MypageReplyRes> list = mypageSVC.mypageReply(loginMember.getId());
		
		StringBuffer html = new StringBuffer();
			
			html.append("<h3 class='mypage_content_title'>내가 쓴 댓글</h3>");
			html.append("<hr>");
			html.append("<div class='mypage_content'>");
			html.append("  <table class='reply__table'> ");
			html.append("    <tr>");
			html.append("      <th class='reply__cell'>번호</th>");
			html.append("      <th class='reply__cell'>댓글내용</th>");
			html.append("      <th class='reply__cell'>작성일</th>");
			html.append("      <th class='reply__cell'>좋아요</th>");
			html.append("       <!-- <th class='mypagereply__title5'></th> -->");
			html.append("    </tr>");
		list.forEach(rec->{
			html.append("    <tr>");
			html.append("      <td class='reply__cell'>"+rec.getRnum()+"</td>");
			html.append("      <td class='reply__cell'><a href='/board/post/"+rec.getBnum()+"'>"+rec.getRcontent()+"</a></td>");
			html.append("      <td class='reply__cell'>"+rec.getRcdate()+"</td>");
			html.append("      <td class='reply__cell'>"+rec.getBgood()+"</td>");
			html.append("      <!-- <td th:text='5번째칸' class='mypagereply__text'></td> -->");
			html.append("    </tr>");
		});
			html.append("  </table>");
			html.append("</div>");
		
		Result result;
		result = new Result("00","OK",html);
		
	//	list.forEach(ele->{
	//		log.info("ele:{}",ele);
	//	});
		return result;
	}
	
	//회원탈퇴양식
	@GetMapping("/mypageDel")
	public Result mypageDel() {
		
		Result result;
		
		StringBuffer html = new StringBuffer();
		html.append("<h3 class='mypage_content_title'>회원탈퇴</h3>");
		html.append("<hr>");
		html.append("<div class='mypage_content'>");
		html.append("  <form action='/mypage/mypageDel' method='post' class='findId'><input type='hidden' name='_method' value='delete\'>");
		html.append("    <h1 class='findId__title'></h1>");
		html.append("    <!-- <p class='login__errormsg' th:errors='*{global}'></p> -->");
		html.append("    <div class='findId__form'>");
		html.append("      <span class='findId__text'>비밀번호 확인</span>");
		html.append("      <input class='findId__input' type='text' name='pw'>");
		html.append("    </div>");
		html.append("    <button class='findId__btn' type='submit'>회원탈퇴</button>");
		html.append("  </form>");
		html.append("</div>");
		
		result = new Result("00","OK",html);
		
		return result;
	}

}
