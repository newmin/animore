package com.proj.animore.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.form.Result;
import com.proj.animore.svc.MemberSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/join/api")
@RestController
public class APIJoinController {
	
	private final MemberSVC memberSVC;
	
	//아이디중복체크
	@GetMapping("/id")
	public Result idDupChk(
			@RequestParam String id){
		
		Result result;
		
		boolean isExistId = memberSVC.isExistId(id);
		
		if(!isExistId) {
			result = new Result("00","사용가능한 아이디입니다.",null);
		}else {
			result = new Result("01","사용불가한 아이디입니다.",null);	
		}
		
		return result;
	}

	//닉네임중복체크
	@GetMapping("/nickname")
	public Result nicknameDupChk(
			@RequestParam String nickname){
		
		Result result;
		
		boolean isExistNickname = memberSVC.isExistNickname(nickname);
		
		if(!isExistNickname) {
			result = new Result("00","사용가능한 별칭입니다.",null);
		}else {
			result = new Result("01","사용불가한 별칭입니다.",null);	
		}
		
		return result;
	}
	
}
