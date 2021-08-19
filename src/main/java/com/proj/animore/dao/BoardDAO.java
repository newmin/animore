package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.vo.PostVO;

public interface BoardDAO {

	//게시글 등록
	void addBoard(BoardDTO boardDTO);
	
	//게시글 조회(by bnum)
	PostVO findBoardByBnum(int bnum);
	
	//게시글 조회(by id)
	List<BoardDTO> findBoardById(String id);
	
	//게시글 조회(by btitle)
	List<BoardDTO> findBoardByBtitle(String btitle);
	
	//게시글 조회(by bcontent)
	List<BoardDTO> findBoardByBcontent(String bcontent);
	
	//게시글 수정
	BoardDTO modifyBoard(int bnum,BoardDTO boardDTO);
	
	//게시글 삭제
	void deleteBoard(int bnum);
	
	//게시글 전체목록(by bcategory)
	List<BoardDTO> list(String bcategory);
}
