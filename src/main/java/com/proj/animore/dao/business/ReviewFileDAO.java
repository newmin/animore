package com.proj.animore.dao.business;

import java.util.List;

import com.proj.animore.dto.business.BusiUploadFileDTO;
import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

public interface ReviewFileDAO {

	//리뷰파일등록
	void registReviewFile(int rnum,List<BusiUploadFileDTO> files);
	//리뷰파일조회
	List<ReviewReq> findReviewFile(int rnum);
	//리뷰파일수정
	List<ReviewReq> updateReviewFile(ReviewDTO reviewDTO);
	//리뷰파일삭제
	List<ReviewReq> removeReviewFile(int rnum);
}
