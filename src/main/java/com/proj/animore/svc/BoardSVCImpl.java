package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.BoardDAO;
import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.BoardReqDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC {

	private final BoardDAO boardDAO;
	
	//게시글등록
	@Override
	public BoardReqDTO addBoard(String id,BoardDTO boardDTO) {
	return 	boardDAO.addBoard(id, boardDTO);
		
	}
	//게시글조회
	@Override
	public BoardReqDTO findBoardByBnum(Integer bnum) {
		return boardDAO.findBoardByBnum(bnum);
		
	}
	//조회수
	@Override
	public void upBhit(Integer bnum) {
		boardDAO.upBhit(bnum);
		
	}
	//게시글검색(by id)
	@Override
	public List<BoardReqDTO> findBoardById(String id) {
		return boardDAO.findBoardById(id);
	}
	//게시글검색(by btitle)
	@Override
	public List<BoardReqDTO> findBoardByBtitle(String bcategory,String btitle) {
		return boardDAO.findBoardByBtitle(bcategory,btitle);
	}
	//게시글검색(by bcontent)
	@Override
	public List<BoardReqDTO> findBoardByBcontent(String bcategory,String bcontent) {
		return boardDAO.findBoardByBcontent(bcategory,bcontent);
	}
	//게시글검색(by nickname)
	@Override
	public List<BoardReqDTO> findBoardByNickname(String bcategory,String nickname) {
		return boardDAO.findBoardByNickname(bcategory,nickname);
	}
	//게시글수정
	@Override
	public BoardReqDTO modifyBoard(int bnum, BoardDTO boardDTO) {
		return boardDAO.modifyBoard(bnum, boardDTO);
	}
	//게시글삭제
	@Override
	public void deleteBoard(int bnum) {
		boardDAO.deleteBoard(bnum);
		
	}
	//게시글전체목록(by bcategory)
	@Override
	public List<BoardReqDTO> list(String bcategory) {
		return boardDAO.list(bcategory);
	}
	
	//게시글전체목록(좋아요순나열)
	@Override
	public List<BoardReqDTO> bgoodList(String bcategory) {
		return boardDAO.bgoodList(bcategory);
	}
	
	//댓글수조회
	@Override
	public Integer reqBreply(Integer bnum) {
		return boardDAO.reqBreply(bnum);
	}
	//공지추가
	@Override
	public void addNotice(int bnum) {
		boardDAO.addNotice(bnum);
		
	}
	//공지삭제
	@Override
	public void delNotice(int bnum) {
		boardDAO.delNotice(bnum);
		
	}
	//공지리스트
	@Override
	public List<BoardReqDTO> noticeList(String bcategory) {
		return boardDAO.noticeList(bcategory);
	}
	
	//공지글여부
	@Override
	public boolean isNotice(int bnum) {
		return boardDAO.isNotice(bnum);
	}
}
