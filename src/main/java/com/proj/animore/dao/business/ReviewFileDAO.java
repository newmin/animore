package com.proj.animore.dao.business;

import java.util.List;

import com.proj.animore.dto.business.BusiUploadFileDTO;
import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

public interface ReviewFileDAO {

	//리뷰파일등록
	void registReviewFile(List<BusiUploadFileDTO> files);
	//리뷰파일조회
	List<BusiUploadFileDTO> getReviewFiles(int rnum);
	//리뷰파일수정
	void updateReviewFiles(List<BusiUploadFileDTO> files);
	//리뷰파일삭제
	void removeReviewFiles(int rnum);
	//첨부파일명 불러오기
	List<String> getStore_Fname(int rnum);
	//첨부파일 개별 삭제
	void removeEachFile(int fnum);
	
}
