package com.proj.animore.dao.board;

import java.util.List;

import com.proj.animore.dto.board.BoardUploadFileDTO;

public interface BoardUploadFileDAO {
	/**
	 * 업로드 파일추가
	 * @param list
	 */
	void addFile(BoardUploadFileDTO boardUploadFileDTO);
	void addFiles(List<BoardUploadFileDTO> list);
	
	/**
	 * 업로드 파일 조회
	 * @param pid
	 * @returnO
	 */
	List<BoardUploadFileDTO> getFiles(Integer bnum);
	
	/**
	 * 업로드파일 삭제 by 게시글번호
	 * @param bnum
	 */
	void deleteFileByBnum(Integer bnum);
	
	List<String> getStore_Fname(Integer bnum);
	
}
