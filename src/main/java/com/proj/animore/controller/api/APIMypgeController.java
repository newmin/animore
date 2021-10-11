package com.proj.animore.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.dto.ChangPwReq;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.MypageReplyRes;
import com.proj.animore.dto.board.BoardReqDTO;
import com.proj.animore.dto.board.GoodBoardDTO;
import com.proj.animore.dto.business.BusinessLoadDTO;
import com.proj.animore.dto.business.FavoriteReq;
import com.proj.animore.dto.business.ReviewReq;
import com.proj.animore.form.ChangePwForm;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.ModifyForm;
import com.proj.animore.form.OutMemberForm;
import com.proj.animore.form.Result;
import com.proj.animore.svc.MemberSVC;
import com.proj.animore.svc.MypageSVC;
import com.proj.animore.svc.board.BoardSVC;
import com.proj.animore.svc.board.GoodBoardSVC;
import com.proj.animore.svc.business.BusinessSVC;
import com.proj.animore.svc.business.FavoriteSVC;
import com.proj.animore.svc.business.ReviewSVC;

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
	private final MemberSVC memberSVC;
	private final BusinessSVC businessSVC;
	private final GoodBoardSVC goodBoardSVC;
	private final FavoriteSVC favoriteSVC;
	
	//내 리뷰 조회
	@GetMapping("/myreview")
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
		
		Result result = new Result("00","성공",list);
		
		return result;
	}
	
	//내 좋아요 조회
	//@GetMapping("/mypageGood")
	public Result goodBoardList(HttpServletRequest request) {
	
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		log.info("loginMember:{}",loginMember);
		
		List<GoodBoardDTO> list = goodBoardSVC.goodBoardList(loginMember.getId());
		
		Result result;
		result = new Result("00","OK",list);
		
		return result;
	}
	
	//개인정보수정화면
	//@GetMapping("/mypageModify")
	public Result mypageModi (HttpServletRequest request){
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		log.info("loginMember:{}",loginMember);
		String id = loginMember.getId();
		MemberDTO memberDTO = memberSVC.findMemberById(id);
	
		return new Result("00","OK",memberDTO);

	}
	
	//내정보 개인정보수정 처리
	//@PatchMapping("/mypageModify")
	public Result mypageModify(HttpServletRequest request,
			@RequestBody @Valid ModifyForm modidyfyForm,
			BindingResult bindingResult) { 
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		
		log.info(modidyfyForm.toString());
		
		MemberDTO oldMemberDTO = memberSVC.findMemberById(id);
		if(!oldMemberDTO.getPw().equals(modidyfyForm.getPwChk())) {
			bindingResult.reject("pw","현재 비밀번호가 일치하지 않습니다.");
			return new Result("01","비밀번호가 일치하지 않습니다.",null);
		}

		MemberDTO memberDTO = new MemberDTO();
		BeanUtils.copyProperties(modidyfyForm, memberDTO);
		
		memberSVC.modifyMember(id, memberDTO);

		Result result;
		
		result = new Result("00","OK",memberDTO);
		
		return result;
	}
	
	//내업체목록
	//@GetMapping("/mybusilist")
	public Result mypagebusilist (HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		
		List<BusinessLoadDTO> mybusiList = businessSVC.mybusiList(loginMember.getId());
		
		log.info(mybusiList.toString());
		
		Result result;
		result = new Result("00","OK",mybusiList);
		
		return result;
	}
	
	//비밀번호 변경
	@GetMapping("/mypagePwModify")
	public Result mypage__mypwBtn(HttpServletRequest request) {
		
	HttpSession session = request.getSession(false);
	LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
	log.info("loginMember:{}",loginMember);
	
	MemberDTO memberDTO = new MemberDTO();
	
	String id = loginMember.getId();
	memberDTO = memberSVC.findMemberById(id);
	
	Result result;
	
	result = new Result("00","OK",memberDTO);
	
	return result;
	
	}
	//비밀번호 수정처리
	@PatchMapping("/mypagePwModify")
	public Result mypagePwModify(@Valid @RequestBody ChangePwForm changePwForm,
			BindingResult bindingResult,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		log.info("loginMember:{}",loginMember);
		
		ChangPwReq changPwReq = new ChangPwReq();
		
		BeanUtils.copyProperties(changePwForm,changPwReq);
		
		MemberDTO memberDTO = memberSVC.changePW(loginMember.getId(), changPwReq);
		//todo이전 비밀번호 같은지 확인
		if(!memberDTO.getPw().equals(changePwForm.getPw())) {
			bindingResult.reject("pwChk","현재 비밀번호가 일치하지 않습니다.");
			return new Result("03","현재 비밀번호가 일치하지 않습니다.",null);
		}
		Result result;
		//새 비밀번호 확인
		if(!changePwForm.getPwChk().equals(changePwForm.getPwChk2())) {
			bindingResult.reject("pwChk","새 비밀번호 확인이 일치하지 않습니다.");
			return new Result("01","새비밀번호 확인이 일치하지 않습니다.",null);
		}
		//이전 비밀번호와 변경할 비밀번호가 동일한지 체크
		if(changePwForm.getPw().equals(changePwForm.getPwChk())) {
			
			bindingResult.reject("error.member.changePw", "이전 비밀번호와 동일합니다.");
			return new Result("02","이전 비밀번호와 동일합니다.",null);

			
		}
		
		log.info(changePwForm.toString());
		log.info(changPwReq.toString());
		
		result = new Result("00","OK",memberDTO);
		
		return result;
	}
	
	//회원탈퇴양식
	@GetMapping("/mypageDel")
	public Result mypageDel(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Result result;
		
		result = new Result("00","OK",null);
		
		return result;
	}
	
  //회원탈퇴처리(회원상태타입 수정(Active→Without))
  @PatchMapping("/mypageDel")
  public Result mypageDel(
		  @Valid @RequestBody OutMemberForm outMemberForm,
        HttpServletRequest request) {
     
     HttpSession session = request.getSession(false);
     if(session == null && session.getAttribute("loginMember")==null) {
    	 return new Result("01","로그인이 만료되었습니다. 재로그인 후 시도해주세요.",null);
     }
     
     String pwChk = outMemberForm.getPwChk();

     if(pwChk == null || pwChk.trim().length() == 0) {
        return new Result("02","비밀번호를 입력해주세요",null) ;
     }
     
     LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
     String id = loginMember.getId();
     
     //비밀번호 확인
     if(!memberSVC.findMemberById(id).getPw().equals(pwChk)) {
    	 return new Result("03","비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요",null);
     }

     log.info("회원탈퇴:{}",id);
     memberSVC.outMember(id);
     
     session.invalidate();
     
     return new Result("00","회원탈퇴 정상적으로 처리되었습니다. 그동안 이용해주셔서 진심으로 감사드립니다.",null);
  }
	
	
}
