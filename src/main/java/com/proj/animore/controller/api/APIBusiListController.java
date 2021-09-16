package com.proj.animore.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.dto.business.BusinessLoadDTO;
import com.proj.animore.dto.business.HtagBusiListReq;
import com.proj.animore.form.Result;
import com.proj.animore.svc.business.BusinessSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class APIBusiListController {

	private final BusinessSVC businessSVC;
	
	@GetMapping("/bhospital/api")
	public Result<List<BusinessLoadDTO>> bhospitalTagSearch(
			HtagBusiListReq htblr) {
		
		List<BusinessLoadDTO> list = businessSVC.busiListHospitalTag("bhospital", htblr);
		log.info("hTagBusiList:{}",list);
		
		return new Result<List<BusinessLoadDTO>>("00","OK",list);
	}
	
}
