package com.proj.animore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.RboardListReqDTO;
import com.proj.animore.form.BoardForm;
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
	
	//게시글 목록출력
	@GetMapping("/{bcategory}")
	public String boardList(@ModelAttribute BoardForm boardForm,
							@PathVariable String bcategory,
							Model model) {
	     if(bcategory.equals("Q"))   bcategory="Q";
	     if(bcategory.equals("M"))   bcategory="M";
	     if(bcategory.equals("F"))   bcategory="F";
	     if(bcategory.equals("P"))   bcategory="P";
		
	     List<BoardDTO> list = boardSVC.list(bcategory);
	     model.addAttribute("boardForm",list);
	     
		return "board/board";
	}
	
	//게시글 조회
	@GetMapping("/post/{bnum}")
	public String post(@PathVariable Integer bnum,
										Model model,
										Model reply) {
		
		BoardDTO boardDTO = boardSVC.findBoardByBnum(bnum);
		model.addAttribute("post",boardDTO);
		//게시글 조회시 해당 게시글의 댓글목록도 함께 불러옴.
		List<RboardListReqDTO> replyList = rboardSVC.all(bnum);
		model.addAttribute("reply", replyList);
		
		log.info("BoardDTO:{}",boardDTO);
		log.info("replyList:{}",replyList);
		
		return "board/boardDetail";
	}
	
	//게시글 작성화면 출력
	@GetMapping("/add")
	public String addPost() {
		
		return "board/addBoardForm";
	}
}
