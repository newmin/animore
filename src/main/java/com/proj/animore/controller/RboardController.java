package com.proj.animore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.dto.RboardDTO;
import com.proj.animore.dto.RboardListReqDTO;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.RboardAddReq;
import com.proj.animore.form.RboardModiReq;
import com.proj.animore.form.Result;
import com.proj.animore.svc.BoardSVC;
import com.proj.animore.svc.RboardSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 댓글CRUD 관련 컨트롤러
 * 
 * @author hjlee0820
 */
@Slf4j
@RestController
@RequestMapping("/rboard")
@RequiredArgsConstructor
public class RboardController {

	private final RboardSVC rboardSVC;
	
//댓글등록처리   post
	@PostMapping("/{bnum}/{id}")
	public Result register(
			@PathVariable int bnum,
			@PathVariable String id,
			@Valid @RequestBody RboardAddReq rar,
			HttpServletRequest request) {
		// rnum : 시퀀스
		// bnum : 게시글번호
		// rgroup : 댓글그룹
		// 댓글단계 : 부모댓글(댓글그룹)의 댓글단계 +1 해줘야함
		// Content : 댓글내용

		//우선 할것 : 요청받기, 데이터 옮겨담기, 저장하기
		//나중에 덧붙일것 : 결과 리턴받아서 보여주기, 무결성검사, 등등
		//요청의 세션 받기
		HttpSession session = request.getSession(false);
		
		Result result;
		//로그인 하지 않고 요청했다면
		if(session == null) {
			result = new Result("01","댓글입력을 위해 로그인이 필요합니다.",null);
			return result;
		}
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();

		RboardDTO rboardDTO = new RboardDTO();
		BeanUtils.copyProperties(rar,rboardDTO);
		
		//저장하고 댓글목록 갱신하기 위해 리스트에 담음
  	List<RboardListReqDTO> replyList = rboardSVC.register(bnum, loginMemberId, rboardDTO);
  	
  	
  	
  	result = new Result("00","성공",replyList);
  	return result;
	}

//댓글1개조회(댓글수정 클릭하는 순간 댓글정보 전달)		get
//
	@GetMapping("/{bnum}/{rnum}")
	public Result findByRnum(
			@PathVariable int bnum,
			@PathVariable int rnum) {
		
		RboardListReqDTO rboardReqDTO = rboardSVC.findByRnum(bnum, rnum);
		Result result = new Result();
		if (rboardReqDTO == null) {
			result.setRtcd("01");
			result.setRtmsg("존재하는 댓글이 없습니다.");
			result.setData(rboardReqDTO);
		} else {
			result.setRtcd("00");
			result.setRtmsg("성공");
			result.setData(rboardReqDTO);
		}
		return result;
	}
	
//댓글수정처리 patch
	@PatchMapping("/{bnum}/{rnum}/{id}")
	public Result modify(
			@PathVariable int bnum,
			@PathVariable int rnum,
			@PathVariable String id,			
			@RequestBody RboardModiReq rmr) {
		//rcontent
		log.info("modify:{}",bnum);
		log.info("modify:{}",rnum);
		log.info("modify:{}",id);
		log.info("modify:{}",rmr);
		
		RboardDTO rboardDTO = new RboardDTO();
		BeanUtils.copyProperties(rmr,rboardDTO);
		List<RboardListReqDTO> modifiedRboardDTO = rboardSVC.modify(bnum, rnum, id, rboardDTO);
		
		
		Result result = new Result();
		if (modifiedRboardDTO == null) {
			result.setRtcd("01");
			result.setRtmsg("존재하는 댓글이 없습니다.");
		} else {
			result.setRtcd("00");
			result.setRtmsg("성공");
			result.setData(modifiedRboardDTO);
		}
		
		return result;
	}

// 엑셀파일에 삭제시 댓글번호 필요하다고 적어야함
// 댓글삭제처리 delete
	@DeleteMapping("/{bnum}/{rnum}/{id}")
	public Result del(
			@PathVariable int bnum,
			@PathVariable int rnum,
			@PathVariable String id) {
		
		List<RboardListReqDTO> list = rboardSVC.del(bnum, rnum, id);
		Result result = new Result();
		if (1 == 0) {
			result.setRtcd("01");
			result.setRtmsg("삭제하고자 하는 댓글이 없습니다.");
			result.setData(null);
		} else {
			result.setRtcd("00");
			result.setRtmsg("성공");
			result.setData(list);
		}
		return result;
	}

	// 댓글목록조회 by 게시글
	@GetMapping("/{bnum}")
	public Result all(@PathVariable int bnum) {
		List<RboardListReqDTO> list = rboardSVC.all(bnum);
		Result result = new Result();
		if (list.size() == 0) {
			result.setRtcd("01");
			result.setRtmsg("댓글 정보가 없습니다.");
		} else {
			result.setRtcd("00");
			result.setRtmsg("성공");
			result.setData(list);
		}

		return result;
	}

}
