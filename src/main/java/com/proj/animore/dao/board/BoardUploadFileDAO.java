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
	BoardUploadFileDTO getFileByfnum(Integer fnum);
	BoardUploadFileDTO getFileBySfname(String sfname);
	
	/**
	 * 업로드파일 삭제 by 게시글번호
	 * @param bnum
	 */
	void deleteFileByBnum(Integer bnum);
	/**
	 * 참조번호로 첨부파일 가져오기
	 * @param bnum
	 * @return
	 */
	List<String> getStore_Fname(Integer bnum);
	/**
	 * 업로드파일 삭제 by fnum
	 * @param fnum
	 */
	void deleteFileByFnum(Integer fnum);
	
	/**
	 * 업로드파일 삭제 by sfname
	 * @param sfname
	 */
	void deleteFileBySfname(String sfname);
}
