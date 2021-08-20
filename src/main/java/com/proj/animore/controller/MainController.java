package com.proj.animore.controller;

import com.proj.animore.dto.BusinessDTO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

	@GetMapping("/{bcategory}")
	public String list(@ModelAttribute BusinessDTO businessDTO,@PathVariable String bcategory){
		// 업체카테고리가 busiDTO에는 포함 안되어있음. 사장id(fk)로 조인해서 찾아야함.
		//		public String list(@ModelAttribute BusinessDTO businessDTO,@PathVariable String bcategory){
		return "map/busiList";
	}

	@GetMapping("/zx")
	public String tempList() {
		return "map/busiListTemp";
	}
	@GetMapping("/zxc")
	public String inquire() {
		return "map/inquireBusiDetail";
	}
	
}
