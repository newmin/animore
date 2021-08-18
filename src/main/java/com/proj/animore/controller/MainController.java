package com.proj.animore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/zxc")
	public String inquire() {
		return "map/inquireBusiDetail";
	}
}
