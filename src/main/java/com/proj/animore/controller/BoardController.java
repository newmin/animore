package com.proj.animore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.form.BoardForm;
import com.proj.animore.form.Code;
import com.proj.animore.svc.BoardSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardSVC boardSVC;
	
	//TODO 게시글 등록시 카테고리 읽어오려면 만들어야하는 코드인데 활성화하면 게시글목록이 안나옴...
//	@ModelAttribute("bcategory")
//	public List<Code> bcategory(){
//		List<Code> list = new ArrayList<>();
//		list.add(new Code("Q","Q&A"));
//		list.add(new Code("F","자유게시판"));
//		list.add(new Code("M","벼룩시장"));
//		list.add(new Code("P","내새끼 보세요"));
//		return list;
//	}
	
	//게시글 목록출력
	@GetMapping("/{bcategory}")
	public String boardList(@PathVariable String bcategory,
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
										Model model) {
	
		BoardDTO boardDTO = boardSVC.findBoardByBnum(bnum);
		model.addAttribute("post",boardDTO);

		log.info("BoardDTO:{}",boardDTO);
		
		return "board/boardDetail";
	}
	
	//게시글 작성화면 출력
	@GetMapping("/add")
	public String addPost() {
		
		return "board/addBoardForm";
	}
	
	//게시글 등록처리
	@PostMapping("/add")
	public String addpost(@ModelAttribute BoardForm boardForm,
							BoardDTO boardDTO,
						BindingResult bindingResult) {
		boardSVC.addBoard(boardDTO);
		return "redirect :/";
		
	}
	}

