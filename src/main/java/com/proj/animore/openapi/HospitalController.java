package com.proj.animore.openapi;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class HospitalController {

	private final HospitalOpenAPI2 hospital;
	
	@ResponseBody
	@GetMapping("/hospital")
	public String hospital(@ModelAttribute HospitalParam hospitalParam) throws IOException {
//		String hospitalXML = hospital.getHospital(hospitalParam);

//		HospitalOpenAPI hoa = new HospitalOpenAPI();
		HospitalParam hp = new HospitalParam();
		hp.setNumOfRows(hospitalParam.getNumOfRows());
		hp.setPageNo(hospitalParam.getPageNo());
		
		List<Hospital> hList = hospital.getHospital(hp);
		
		return hList.toString();
	}
}
