package com.proj.animore.dao.business;

import java.util.List;

import com.proj.animore.dto.business.BusiUploadFileDTO;

public interface BusinessFileDAO {

	//업체파일등록
	void addBusiFile(List<BusiUploadFileDTO> files);
	//업체파일조회
	List<BusiUploadFileDTO> getBusiFiles(int bnum);
	//업체파일수정
	void updateBusiFiles(List<BusiUploadFileDTO> files);
	//해당 업체파일 전체삭제
	void removeBusiFiles(int bnum);
	//첨부파일명 불러오기
	List<String> getStore_Fname(int bnum);
	//첨부파일 개별 삭제
	void removeEachFile(int fnum);
}
