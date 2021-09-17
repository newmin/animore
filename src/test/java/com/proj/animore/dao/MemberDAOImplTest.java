package com.proj.animore.dao;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proj.animore.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class MemberDAOImplTest {

	@Autowired
	private MemberDAO mdao; 
	
	@Test
	@Order(1)
	@DisplayName("회원가입")
//	@Disabled
	void joinMember() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("zxctest@zxc.com");
		memberDTO.setPw("zxc123cx!");
		memberDTO.setName("김구용");
		memberDTO.setBirth(LocalDate.parse("1995-02-23"));
		memberDTO.setGender("M");
		memberDTO.setTel("010-1234-5678");
		memberDTO.setEmail("zxc@csad.com");
		memberDTO.setAddress("김강김구김상");
		memberDTO.setNickname("김김김");
		memberDTO.setMtype("N");
		
		mdao.joinMember(memberDTO);
		
		MemberDTO result = mdao.findByIdPw("zxctest@zxc.com", "zxc123cx!");
		log.info(result.toString());
	}
	
	@Test
	@Order(2)
	@DisplayName("회원조회")
	void findMemberById() {
		String id="zxctest@zxc.com";
		
		MemberDTO result = mdao.findMemberById(id);
		
		log.info(result.toString());
		
	}
	
	@Test
	@Order(3)
	@DisplayName("회원정보수정")
	void modifyMember() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("zxctest@zxc.com");
		memberDTO.setPw("zxc123cx!");
		memberDTO.setName("김구용수정");
		memberDTO.setBirth(LocalDate.parse("1995-02-23"));
		memberDTO.setGender("M");
		memberDTO.setTel("010-1234-5678");
		memberDTO.setEmail("zxc@csad.com");
		memberDTO.setAddress("김강김구김상수정");
		memberDTO.setNickname("김김김수정");
		
		mdao.modifyMember(memberDTO.getId(), memberDTO);
		
		MemberDTO result = mdao.findByIdPw("zxctest@zxc.com", "zxc123cx!");
		log.info(result.toString());
	}
	
	@Test
	@Order(4)
	@DisplayName("회원탈퇴")
	void deleteMember() {
		String id="zxctest@zxc.com";
		
		mdao.deleteMember(id);
		log.info(id);
		
	}
}
