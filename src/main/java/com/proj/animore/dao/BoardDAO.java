package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.BoardReqDTO;

public interface BoardDAO {

	//게시글 등록
	BoardReqDTO addBoard(String id,BoardDTO boardDTO);
	
	//게시글 조회(by bnum)
	BoardReqDTO findBoardByBnum(Integer bnum);
	
	//조회수 증가
	void upBhit(Integer bnum);
	
	//게시글 조회(by id)
	List<BoardReqDTO> findBoardById(String id);
	
	//게시글 조회(by btitle)
	List<BoardReqDTO> findBoardByBtitle(String bcategory,String btitle);
	
	//게시글 조회(by bcontent)
	List<BoardReqDTO> findBoardByBcontent(String bcategory,String bcontent);
	
	//게시글 조회(by nickname)
	List<BoardReqDTO> findBoardByNickname(String bcategory,String nickname);
	
	//게시글 수정
	BoardReqDTO modifyBoard(int bnum,BoardDTO boardDTO);
	
	//게시글 삭제
	void deleteBoard(int bnum);
	
	//게시글 전체목록(by bcategory)
	List<BoardReqDTO> list(String bcategory);
	
	//게시글 전체목록(좋아요순)
	List<BoardReqDTO> bgoodList(String bcategory);
	
	//댓글수 증가
	void upRcount(Integer bnum);
	
	//댓글수 감소
	void downRcount(Integer bnum);
	
	//댓글수 조회
	Integer reqBreply(Integer bnum);
}
