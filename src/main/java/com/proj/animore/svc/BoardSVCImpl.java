package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.BoardDAO;
import com.proj.animore.dto.BoardDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC {

	private final BoardDAO boardDAO;
	
	//게시글등록
	@Override
	public void addBoard(BoardDTO boardDTO) {
		boardDAO.addBoard(boardDTO);
		
	}
	//게시글조회
	@Override
	public BoardDTO findBoardByBnum(Integer bnum) {
		return boardDAO.findBoardByBnum(bnum);
		
	}
	//게시글검색(by id)
	@Override
	public List<BoardDTO> findBoardById(String id) {
		return boardDAO.findBoardById(id);
	}
	//게시글검색(by btitle)
	@Override
	public List<BoardDTO> findBoardByBtitle(String btitle) {
		return boardDAO.findBoardByBtitle(btitle);
	}
	//게시글검색(by bcontent)
	@Override
	public List<BoardDTO> findBoardByBcontent(String bcontent) {
		return boardDAO.findBoardByBcontent(bcontent);
	}
	//게시글수정
	@Override
	public BoardDTO modifyBoard(int bnum, BoardDTO boardDTO) {
		return boardDAO.modifyBoard(bnum, boardDTO);
	}
	//게시글삭제
	@Override
	public void deleteBoard(int bnum) {
		boardDAO.deleteBoard(bnum);
		
	}
	//게시글전체목록(by bcategory)
	@Override
	public List<BoardDTO> list(String bcategory) {
		return boardDAO.list(bcategory);
	}

}
