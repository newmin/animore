package com.proj.animore.dao;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proj.animore.dao.board.RboardDAO;
import com.proj.animore.dto.board.RboardDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RboardDAOImplTest {
	
	@Autowired
	private RboardDAO rdao;
	
	@Test
	@DisplayName("댓글등록")
	@Disabled
	void register() {
//		int bnum, String id, RboardDTO rboardDTO
//		List<RboardDTO>
		int bnum=1;
		String id="normal@zxc.com";
		RboardDTO rDTO = new RboardDTO();
		
		rDTO.setBnum(bnum);
		rDTO.setId(id);
		rDTO.setRcontent("테스트");
		rDTO.setRgroup(1);
		rDTO.setRstep(1);
		
		log.info("rList:{}",rdao.register(bnum, id, rDTO));
	}
	
	@Test
	@DisplayName("댓글조회 by 댓글번호")
	@Disabled
	void findbyRnum() {
		
		log.info("findbyRnum:{}",rdao.findbyRnum(1,1));
	}
	
	@Test
	@DisplayName("댓글수정처리")
	@Disabled
	void modify() {
//		int bnum, int rnum, String id, RboardDTO rboardDTO
		
		int bnum=1;
		int rnum=6;
		String id="normal@zxc.com";
		RboardDTO rDTO = new RboardDTO();
		
		rDTO.setBnum(bnum);
		rDTO.setRnum(rnum);
		rDTO.setId(id);
		rDTO.setRcontent("테스트수정");
//		rDTO.setRgroup("1");
//		rDTO.setRstep("1");
		
		log.info("rList:{}",rdao.modify(bnum, rnum, id, rDTO));
	}
	
	@Test
	@DisplayName("댓글삭제처리")
	@Disabled
	void del() {
		
		
//		log.info("rList:{}",rdao.del(1, 6, "normal@zxc.c"));	//틀린아이디,	삭제안되고 목록갱신만됨
		log.info("rList:{}",rdao.del(1, 6, "normal@zxc.com"));	//맞는아이디,	삭제되고 목록갱신됨
	}
	
	@Test
	@DisplayName("댓글목록")
	void all() {
		// 1번 게시글의 댓글목록
		log.info("rList:{}",rdao.all(1));
	}
}
