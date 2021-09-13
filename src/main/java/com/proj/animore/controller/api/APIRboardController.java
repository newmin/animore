package com.proj.animore.controller.api;

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

import com.proj.animore.dto.board.RboardDTO;
import com.proj.animore.dto.board.RboardListReqDTO;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.RboardAddReq;
import com.proj.animore.form.RboardModiReq;
import com.proj.animore.form.RboardReReplyReq;
import com.proj.animore.form.Result;
import com.proj.animore.svc.board.RboardSVC;

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
public class APIRboardController {

	private final RboardSVC rboardSVC;
	
//댓글등록처리   post
	@PostMapping("/{bnum}")
	public Result<List<RboardListReqDTO>> register(
			@PathVariable int bnum,
			@Valid @RequestBody RboardAddReq rar,
			HttpServletRequest request) {
		
		//우선 할것 : 요청받기, 데이터 옮겨담기, 저장하기
		//나중에 덧붙일것 : 결과 리턴받아서 보여주기, 무결성검사, 등등
		//요청의 세션 받기
		HttpSession session = request.getSession(false);
		
		Result<List<RboardListReqDTO>> result;
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();

		RboardDTO rboardDTO = new RboardDTO();
		BeanUtils.copyProperties(rar,rboardDTO);
		
		//저장하고 댓글목록 갱신하기 위해 리스트에 담음
  	List<RboardListReqDTO> replyList = rboardSVC.register(bnum, loginMemberId, rboardDTO);
  	
  	
  	
  	result = new Result<List<RboardListReqDTO>>("00","성공",replyList);
  	return result;
	}

//대댓글작성처리
	@PostMapping("/{bnum}/{rnum}")
	public Result<List<RboardListReqDTO>> reReplyRegister(
			@PathVariable("bnum") int bnum,
			@PathVariable("rnum") int rnum,
			@Valid @RequestBody RboardReReplyReq rrrr,
			HttpServletRequest request) {
		
		log.info("[bnum:{}][rnum:{}][rrrr:{}]",bnum,rnum,rrrr);
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		
		RboardDTO rboardDTO = new RboardDTO();
		BeanUtils.copyProperties(rrrr, rboardDTO);
		
		RboardListReqDTO prboardDTO = rboardSVC.findByRnum(bnum, rnum);
		rboardDTO.setPrnum(prboardDTO.getRnum());
		rboardDTO.setRgroup(prboardDTO.getRgroup());
		rboardDTO.setRstep(prboardDTO.getRstep());
		rboardDTO.setRindent(prboardDTO.getRindent());
		rboardDTO.setId(loginMember.getId());
		
		log.info("[rboardDTO:{}]",rboardDTO);
		List<RboardListReqDTO> replyList = rboardSVC.addReReply(rboardDTO);
		
		return new Result<List<RboardListReqDTO>>("00","성공",replyList);
	}
	
	
	
//댓글1개조회(댓글수정 클릭하는 순간 댓글정보 전달)		get
//
	@GetMapping("/{bnum}/{rnum}")
	public Result findByRnum(
			@PathVariable int bnum,
			@PathVariable int rnum,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
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
	@PatchMapping("/{bnum}/{rnum}")
	public Result<List<RboardListReqDTO>> modify(
			@PathVariable int bnum,
			@PathVariable int rnum,
			@RequestBody RboardModiReq rmr,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false); 
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();
		
		RboardDTO rboardDTO = new RboardDTO();
		BeanUtils.copyProperties(rmr,rboardDTO);
		List<RboardListReqDTO> modifiedRboardDTO = rboardSVC.modify(bnum, rnum, loginMemberId, rboardDTO);
		
		Result<List<RboardListReqDTO>> result = new Result<List<RboardListReqDTO>>();
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
	@DeleteMapping("/{bnum}/{rnum}")
	public Result<List<RboardListReqDTO>> del(
			@PathVariable int bnum,
			@PathVariable int rnum,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false); 

		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		
		List<RboardListReqDTO> list = rboardSVC.del(bnum, rnum, id);
		Result<List<RboardListReqDTO>> result = new Result<List<RboardListReqDTO>>();
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
	public Result<List<RboardListReqDTO>> all(@PathVariable int bnum) {
		List<RboardListReqDTO> list = rboardSVC.all(bnum);
		Result<List<RboardListReqDTO>> result = new Result<List<RboardListReqDTO>>();
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
