package com.proj.animore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpRange;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.BoardReqDTO;
import com.proj.animore.dto.RboardListReqDTO;
import com.proj.animore.form.BoardForm;
import com.proj.animore.form.Code;
import com.proj.animore.form.LoginMember;
import com.proj.animore.svc.BoardSVC;
import com.proj.animore.svc.RboardSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardSVC boardSVC;
	private final RboardSVC rboardSVC;
	
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
	     
		return "board/board";
	}
	
	//게시글 조회
	@GetMapping("/post/{bnum}")
	public String post(@PathVariable Integer bnum,
										Model model,
										HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		//LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		if(session == null) return "redirect:/login";
		
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
		if(session==null) {
			return "/member/login";
		}
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
	@GetMapping("/{bcategory}/{bnum}")
	public String deletePost(@PathVariable String bcategory,
							@PathVariable Integer bnum) {
		
		boardSVC.deleteBoard(bnum);
		
		return "redirect:/board/{bcategory}";
	}
	
}

