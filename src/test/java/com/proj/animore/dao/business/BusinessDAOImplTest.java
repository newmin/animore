package com.proj.animore.dao.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proj.animore.dto.business.BusinessDTO;
import com.proj.animore.dto.business.BusinessLoadDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BusinessDAOImplTest {

	@Autowired
	private BusinessDAO bdao;
	
	@Test
	@DisplayName("업체등록")
	@Order(1)
	private void joinBusi() {
		
		BusinessDTO businessDTO = new BusinessDTO();
		businessDTO.setBbnum("1234");
		businessDTO.setId("busi@test.com");
		businessDTO.setBname("테스트업체1");
		businessDTO.setBaddress("테스트업체1주소");
		businessDTO.setBtel("010-0101-0101");
		businessDTO.setOpenhours("10");
		
		bdao.joinBusi(businessDTO);
		log.info(bdao.mybusiList("busi@test.com").toString());
		
	}
	
	@Test
	@DisplayName("업체조회")
	@Order(2)
	private void findBusiByBnum() {
		
		BusinessLoadDTO bldto = bdao.findBusiByBnum(1);
		log.info(bldto.toString());
		
	}
	
	@Test
	@DisplayName("업체수정")
	@Order(3)
	private void modifyBusi() {
		
	}
	
}
