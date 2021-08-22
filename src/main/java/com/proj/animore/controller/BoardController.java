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
import com.proj.animore.dto.RboardListReqDTO;
import com.proj.animore.form.BoardForm;
import com.proj.animore.form.Code;
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
	
	//TODO 게시글 등록시 카테고리 읽어오려면 만들어야하는 코드인데 활성화하면 게시글목록이 안나옴...
	// -> 임시로 여기랑 addBordForm.html 파일 해당부분 bcategoryCode로 바꿔놓고 th:each 작동되는것 확인했습니다.
	// 		정확한 문법을 아는 건 아닌데 게시글 목록출력 매핑이 /{bcategory}라 이름이 겹쳐서 안되는 것 같네요
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
	
	//게시글 등록처리
	@PostMapping("/add")
	public String addpost(@ModelAttribute BoardForm boardForm,
							BoardDTO boardDTO,
						BindingResult bindingResult) {
		boardSVC.addBoard(boardDTO);
		return "redirect :/";
		
	}
}

