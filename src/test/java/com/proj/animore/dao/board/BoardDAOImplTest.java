package com.proj.animore.dao.board;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proj.animore.dto.board.BoardDTO;
import com.proj.animore.dto.board.BoardReqDTO;
import com.proj.animore.dto.board.SearchDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BoardDAOImplTest {

	@Autowired
	private BoardDAO bdao;
	
	@Test
	@Order(1)
	@DisplayName("게시글등록")
	void addBoard() {
		String id = "normal@zxc.com";
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBcategory("Q");
		boardDTO.setBtitle("단위테스트중");
		boardDTO.setBcontent("단위테스트중!");
		
		BoardReqDTO result = bdao.addBoard(id, boardDTO);
		log.info(result.toString());
		
	}
	@Test
	@Order(2)
	@DisplayName("게시글조회")
	void findBybnum() {
		int bnum = 1;
		BoardReqDTO result = bdao.findBoardByBnum(bnum);
		
		log.info(result.toString());
	}
	
	@Test
	@Order(3)
	@DisplayName("게시글수정")
	void modifypost() {
		int bnum =1;
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBcategory("Q");
		boardDTO.setBtitle("단위테스트수정중");
		boardDTO.setBcontent("단위테스트수정중!");

		int modifyBnum = bdao.modifyBoard(bnum, boardDTO);
		BoardReqDTO result= bdao.findBoardByBnum(modifyBnum);
		
		log.info(result.toString());
		
	}
	@Test
	@Order(4)
	@DisplayName("게시글전체목록")
	void list() {
		String bcategory = "Q";
	List<BoardReqDTO> result = bdao.list(bcategory);
	log.info(result.toString());
	}
	
	@Test
	@Order(5)
	@DisplayName("게시글검색")
	void searchList() {
		SearchDTO searchDTO = new SearchDTO("Q", 1, 1, "btitle", "질문");
		List<BoardReqDTO> result = bdao.list(searchDTO);
		log.info(result.toString());
		
	}
	@Test
	@Order(6)
	@DisplayName("게시글삭제")
	void deletePost() {
		int bnum =1;
		bdao.deleteBoard(bnum);
		log.info(String.valueOf(bnum));
	}
}
