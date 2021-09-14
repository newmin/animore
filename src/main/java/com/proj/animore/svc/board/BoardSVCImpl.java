package com.proj.animore.svc.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proj.animore.common.file.FileStore;
import com.proj.animore.dao.board.BoardDAO;
import com.proj.animore.dao.board.BoardUploadFileDAO;
import com.proj.animore.dto.board.BoardDTO;
import com.proj.animore.dto.board.BoardReqDTO;
import com.proj.animore.dto.board.BoardUploadFileDTO;
import com.proj.animore.dto.board.SearchDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardSVCImpl implements BoardSVC {

	private final BoardDAO boardDAO;
	private final FileStore fileStore;
	private final BoardUploadFileDAO boardUploadFileDAO;
	
	//게시글등록
	@Override
	@Transactional
	public BoardReqDTO addBoard(String id,BoardDTO boardDTO) {
		//게시글등록
		BoardReqDTO savedBoardDTO = boardDAO.addBoard(id,boardDTO);
		//첨부파일 메타정보 저장
		boardUploadFileDAO.addFiles(
				convert(savedBoardDTO.getBnum(),boardDTO.getFiles()));
				
		return savedBoardDTO;
			}
	private List<BoardUploadFileDTO> convert(Integer bnum,List<BoardUploadFileDTO> files){
		for(BoardUploadFileDTO ele : files) {
			ele.setBnum(bnum);
		}
		
		return files;
	}
	
	//답글작성
	@Override
	public int reply(String id,BoardDTO boardDTO) {
		int bnum = boardDAO.reply(id,boardDTO);
		//첨부파일 메타정보 저장
				boardUploadFileDAO.addFiles(
						convert(bnum,boardDTO.getFiles()));
				return bnum;
	}
	
	//게시글조회
	@Override
	public BoardReqDTO findBoardByBnum(Integer bnum) {
		BoardReqDTO boardReqDTO = boardDAO.findBoardByBnum(bnum);
		
		boardReqDTO.setFiles(
				boardUploadFileDAO.getFiles(bnum));
		
		return boardReqDTO;
		
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
	public List<BoardReqDTO> findBoardByBtitle(String bcategory,String btitle,int startRec, int endRec) {
		return boardDAO.findBoardByBtitle(bcategory,btitle,startRec,endRec);
	}
	//게시글검색(by bcontent)
	@Override
	public List<BoardReqDTO> findBoardByBcontent(String bcategory,String bcontent,int startRec, int endRec) {
		return boardDAO.findBoardByBcontent(bcategory,bcontent,startRec,endRec);
	}
	//게시글검색(by nickname)
	@Override
	public List<BoardReqDTO> findBoardByNickname(String bcategory,String nickname,int startRec, int endRec) {
		return boardDAO.findBoardByNickname(bcategory,nickname,startRec,endRec);
	}
	//게시글수정
	@Override
	public int modifyBoard(int bnum, BoardDTO boardDTO) {
		//첨부파일 메타정보 저장
		boardUploadFileDAO.addFiles(
				convert(bnum,boardDTO.getFiles()));
		return boardDAO.modifyBoard(bnum, boardDTO);
	}
	//게시글삭제
	@Override
	public void deleteBoard(int bnum) {
		//서버파일 시스템에 있는 업로드 파일삭제
		log.info("store_fname:{}",boardUploadFileDAO.getStore_Fname(bnum));
		fileStore.deleteFiles(boardUploadFileDAO.getStore_Fname(bnum));
		
		boardDAO.deleteBoard(bnum);

		//업로드 파일 메타정보 삭제
		//boardUploadFileDAO.deleteFileByBnum(bnum);
		
	}
	//게시글전체목록(by bcategory)
	@Override
	public List<BoardReqDTO> list(String bcategory) {
		return boardDAO.list(bcategory);
	}
	@Override
	public List<BoardReqDTO> list(String bcategory, int startRec, int endRec) {
		List<BoardReqDTO> list = boardDAO.list(bcategory, startRec, endRec);
		for(int i=0; i<list.size(); i++) {
			list.get(i).setFiles(
					boardUploadFileDAO.getFiles(list.get(i).getBnum()));
		}
		
		return list;
	}
	//게시글 검색결과 목록
	@Override
	public List<BoardReqDTO> list(SearchDTO searchDTO) {
		List<BoardReqDTO> list = boardDAO.list(searchDTO);
		for(int i=0; i<list.size(); i++) {
			list.get(i).setFiles(
					boardUploadFileDAO.getFiles(list.get(i).getBnum()));
		}
		return list;
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
	//게시판 전체 레코드 수
	@Override
	public long totoalRecordCount() {
		return boardDAO.totlaRecordCount();
	}
	
	//카테고리별 전체 레코드 수
	@Override
	public int totalRecordCount(String bcategory) {
		return boardDAO.totalRecordCount(bcategory);
	}
	//게시판 검색시 레코드 수
	@Override
	public int totalRecordCount(String bcategory, String searchType, String keyword) {
		return boardDAO.totalRecordCount(bcategory, searchType, keyword);
	}
	
}
