package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.BoardReqDTO;

public interface BoardDAO {

	//게시글 등록
	BoardReqDTO addBoard(String id,BoardDTO boardDTO);
	
	//게시글 조회(by bnum)
	BoardReqDTO findBoardByBnum(Integer bnum);
	
	//게시글 조회(by id)
	List<BoardReqDTO> findBoardById(String id);
	
	//게시글 조회(by btitle)
	List<BoardReqDTO> findBoardByBtitle(String btitle);
	
	//게시글 조회(by bcontent)
	List<BoardReqDTO> findBoardByBcontent(String bcontent);
	
	//게시글 수정
	BoardReqDTO modifyBoard(int bnum,BoardDTO boardDTO);
	
	//게시글 삭제
	void deleteBoard(int bnum);
	
	//게시글 전체목록(by bcategory)
	List<BoardReqDTO> list(String bcategory);
}
