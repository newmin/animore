package com.proj.animore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.BoardReqDTO;
import com.proj.animore.dto.RboardListReqDTO;
import com.proj.animore.form.BoardForm;
import com.proj.animore.form.Code;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.svc.BoardSVC;
import com.proj.animore.svc.GoodBoardSVC;
import com.proj.animore.svc.RboardSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardSVC boardSVC;
	private final RboardSVC rboardSVC;
	private final GoodBoardSVC goodBoardSVC;
	
	@ModelAttribute("bcategoryCode")
	public List<Code> bcategory(){
		List<Code> list = new ArrayList<>();
		list.add(new Code("Q","Q&A"));
		list.add(new Code("F","자유게시판"));
		list.add(new Code("M","벼룩시장"));
		list.add(new Code("P","내새끼 보세요"));
		return list;
	}
	
	//게시글 목록출력
	@GetMapping("/{bcategory}")
	public String boardList(@PathVariable String bcategory,
							Model model) {
	     if(bcategory.equals("Q"))   bcategory="Q";
	     if(bcategory.equals("M"))   bcategory="M";
	     if(bcategory.equals("F"))   bcategory="F";
	     if(bcategory.equals("P"))   bcategory="P";
		
	     List<BoardReqDTO> list = boardSVC.list(bcategory);
	     model.addAttribute("boardForm",list);
	     
	    List<BoardReqDTO> nlist = boardSVC.noticeList(bcategory);
	    model.addAttribute("notice",nlist);
	     
		return "board/board";
	}
	
	//게시글 조회
	@GetMapping("/post/{bnum}")
	public String post(@PathVariable Integer bnum,
										Model model,
										HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) return "redirect:/login";
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		//해당회원이 해당글 좋아요여부확인
		int isGoodBoard = goodBoardSVC.isGoodBoard(bnum, id);
	    model.addAttribute("good",isGoodBoard);
	    
	    //해당글 공지여부확인
	    boolean isNotice = boardSVC.isNotice(bnum);
	    model.addAttribute("notice",isNotice);
	    log.info("isNotice:{}",isNotice);
	    
		//조회시 조회수 하나씩 증가
		boardSVC.upBhit(bnum);
		
		BoardReqDTO boardReqDTO = boardSVC.findBoardByBnum(bnum);
		model.addAttribute("post",boardReqDTO);
		
		
		//게시글 조회시 해당 게시글의 댓글목록도 함께 불러옴.
		List<RboardListReqDTO> replyList = rboardSVC.all(bnum);
		model.addAttribute("reply", replyList);
		
		
		log.info("replyList:{}",replyList);
		
		return "board/boardDetail";
	}
	
	//게시글 작성화면 출력
	@GetMapping("/")
	public String addPost(@ModelAttribute BoardForm boardForm,
						HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);

		if(session == null) return "redirect:/login";
		
		return "board/addBoardForm";
	}
	
	//게시글 등록처리
	@PostMapping("/")
	public String addpost(@Valid @ModelAttribute BoardForm boardForm,
							BindingResult bindingResult,
							HttpServletRequest request,
							RedirectAttributes redirectAttributes) {
		
		HttpSession session = request.getSession(false);
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String loginMemberId = loginMember.getId();
		
		
		if(bindingResult.hasErrors()) {return "board/addBoardForm";}
		
		//boardSVC.addBoard(loginMemberId,boardDTO);
		log.info("boardForm:{}",boardForm);
		
		BoardDTO boardDTO = new BoardDTO();
		//boardForm 의 값이 boardDTO에 복사됨
		BeanUtils.copyProperties(boardForm, boardDTO);
		
		BoardReqDTO stored = boardSVC.addBoard(loginMemberId,boardDTO);
		
		redirectAttributes.addAttribute("bnum",stored.getBnum());
		
		
	
		return "redirect:/board/post/{bnum}";

	}
	
	//게시글수정양식출력
	@GetMapping("/modify/{bnum}")
	public String modifyPostForm(@PathVariable Integer bnum,
								Model model) {
		
		BoardReqDTO boardReqDTO = boardSVC.findBoardByBnum(bnum);
		BoardForm boardForm = new BoardForm();
		
		BeanUtils.copyProperties(boardReqDTO, boardForm);
		
		model.addAttribute("boardForm",boardReqDTO);
		
		
		return "board/modifyBoardForm";
	}
	
	//게시글 수정 처리
	@PatchMapping("/{bnum}")
	public String modifyPost(@PathVariable Integer bnum,
							@ModelAttribute BoardForm boardFrom) {

		BoardDTO boardDTO = new BoardDTO();
		BeanUtils.copyProperties(boardFrom, boardDTO);
		
		
		boardSVC.modifyBoard(bnum, boardDTO);
		return "redirect:/board/post/{bnum}";
		
	}
	//게시글 삭제처리
	@ResponseBody
	@DeleteMapping("/{bnum}")
	public Result deletePost(@PathVariable Integer bnum) {
		
		boardSVC.deleteBoard(bnum);
		
		return new Result ("00","ok",bnum);
	}
	//제목으로 게시글 검색
	@ResponseBody
	@GetMapping("/search/title/{bcategory}")
	public Result searchByBtitle(@PathVariable String bcategory,
								@RequestParam String btitle,
								HttpServletRequest request) {
		
		List<BoardReqDTO> list = boardSVC.findBoardByBtitle(bcategory,btitle);
		log.info("bcategory:{}",bcategory);
		Result result = new Result();
		if (list.size() == 0) {
			result.setRtcd("01");
			result.setRtmsg("게시글이 없습니다.");
		} else {
			result.setRtcd("00");
			result.setRtmsg("성공");
			result.setData(list);
		}

		log.info("result:{}",result);
		return result;
	}
	//닉네임으로 게시글 검색
	@ResponseBody
	@GetMapping("/search/nickname/{bcategory}")
	public Result searchByNickname(@PathVariable String bcategory,
									@RequestParam String nickname,
									HttpServletRequest request) {
		
		List<BoardReqDTO> list = boardSVC.findBoardByNickname(bcategory,nickname);
		log.info("bcategory:{}",bcategory);
		Result result = new Result();
		if (list.size() == 0) {
			result.setRtcd("01");
			result.setRtmsg("게시글이 없습니다.");
		} else {
			result.setRtcd("00");
			result.setRtmsg("성공");
			result.setData(list);
		}
		
		log.info("result:{}",result);
		return result;
	}
	//본문으로 게시글 검색
	@ResponseBody
	@GetMapping("/search/content/{bcategory}")
	public Result searchByBcontent(@PathVariable String bcategory,
									@RequestParam String bcontent,
									HttpServletRequest request) {
		
		List<BoardReqDTO> list = boardSVC.findBoardByBcontent(bcategory,bcontent);
		log.info("bcategory:{}",bcategory);
		Result result = new Result();
		if (list.size() == 0) {
			result.setRtcd("01");
			result.setRtmsg("게시글이 없습니다.");
		} else {
			result.setRtcd("00");
			result.setRtmsg("성공");
			result.setData(list);
		}
		
		log.info("result:{}",result);
		return result;
	}
	
	//좋아요클릭시
	@ResponseBody
	@GetMapping("/good/{bnum}")
	public Result addGoodBoard(@PathVariable int bnum,
								HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String id = loginMember.getId();
		int res= goodBoardSVC.isGoodBoard(bnum,id);
		if(res==0) {
			goodBoardSVC.addGoodBoard(id, bnum);
			goodBoardSVC.upGoodBoardCnt(bnum);
		}else {
			goodBoardSVC.delGoodBoard(bnum, id);
			goodBoardSVC.downGoodBoardCnt(bnum);
		}
		Integer bgoodCnt= goodBoardSVC.GoodBoardCnt(bnum);
		Result result = new Result();
		if (res == 0) {
			result.setRtcd("01");
			result.setRtmsg("좋아요 추가되었습니다.");
			result.setData(bgoodCnt);
		} else {
			result.setRtcd("00");
			result.setRtmsg("좋아요 제거되었습니다.");
			result.setData(bgoodCnt);
		}

		return result;
	}
	//공지버튼클릭시
	@ResponseBody
	@GetMapping("/notice/{bnum}")
	public Result postNotice(@PathVariable int bnum) {
		boolean res = boardSVC.isNotice(bnum);
		Result result = new Result();
		if(res ==false) {
			boardSVC.addNotice(bnum);
			result.setRtcd("01");
			result.setRtmsg("공지추가되었습니다.");
		}else {
			result.setRtcd("00");
			result.setRtmsg("공지삭제되었습니다..");
			boardSVC.delNotice(bnum);
		}
		return result;
	}
	
}
	
