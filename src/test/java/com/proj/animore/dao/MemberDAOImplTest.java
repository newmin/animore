package com.proj.animore.dao;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.proj.animore.dto.MemberDTO;
import com.proj.animore.form.Gender;

@SpringBootTest
public class MemberDAOImplTest {

	@Test
	void joinMember() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("zxc@zxc.com");
		memberDTO.setPw("zxc123cx");
		memberDTO.setName("김구용");
		memberDTO.setBirth(LocalDate.parse("1995-02-23"));
		memberDTO.setGender("M");
		memberDTO.setTel("010-1234-5678");
		memberDTO.setEmail("zxc@csad.com");
		memberDTO.setAddress("김강김구김상");
		memberDTO.setNickname("김김김");
		memberDTO.setMtype("N");
	}
}
