package com.proj.animore.openapi;

import java.io.IOException;

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

	private final HospitalOpenAPI hospital;
	
	@ResponseBody
	@GetMapping("/hospital")
	public String hospital(@ModelAttribute HospitalParam hospitalParam) throws IOException {
		String hospitalXML = hospital.getHospital(hospitalParam);
		return hospitalXML;
	}	
}
