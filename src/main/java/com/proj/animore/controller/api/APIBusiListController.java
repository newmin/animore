package com.proj.animore.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.dto.business.BusinessLoadDTO;
import com.proj.animore.dto.business.HtagBusiListReq;
import com.proj.animore.form.Result;
import com.proj.animore.svc.BusinessSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class APIBusiListController {

	private final BusinessSVC businessSVC;
	
	@GetMapping("/bhospital/api")
	public Result<List<BusinessLoadDTO>> bhospitalTagSearch(
			@RequestParam("nightcare") boolean nightcare,
			@RequestParam("visitcare") boolean visitcare,
			@RequestParam("dental") boolean dental,
			@RequestParam("holidayopen") boolean holidayopen,
			@RequestParam("rareani") boolean rareani) {
		
		HtagBusiListReq htblr = new HtagBusiListReq();
		
		htblr.setNightcare(nightcare);
		htblr.setVisitcare(visitcare);
		htblr.setDental(dental);
		htblr.setHolidayopen(holidayopen);
		htblr.setRareani(rareani);
		
		List<BusinessLoadDTO> list = businessSVC.busiListHospitalTag("bhospital", htblr);
		log.info("hTagBusiList:{}",list);
		
		return new Result<List<BusinessLoadDTO>>("00","OK",list);
	}
	
}
