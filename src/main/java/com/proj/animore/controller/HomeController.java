package com.proj.animore.controller;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proj.animore.dto.board.BoardReqDTO;
import com.proj.animore.form.Result;
import com.proj.animore.svc.board.BoardSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

	private final BoardSVC boardSVC;
	@GetMapping("/")
	public String home(Model model) {
		String bcategory ="Q";
		List<BoardReqDTO> list = boardSVC.bgoodList(bcategory);
		model.addAttribute("post",list);
		
		return "index";
	}
	@GetMapping("/main/{bcategory}")
	@ResponseBody
	public Result bestPost(@PathVariable String bcategory) {
		List<BoardReqDTO> list = boardSVC.bgoodList(bcategory);
		Result result = new Result();
		log.info("bcategory{}:",bcategory);
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
		
	}

