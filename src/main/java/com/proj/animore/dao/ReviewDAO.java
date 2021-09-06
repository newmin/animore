package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.ReviewDTO;
import com.proj.animore.dto.ReviewReq;

public interface ReviewDAO {

	//리뷰등록
	List<ReviewReq> registReview(Integer bnum, String id, ReviewDTO reviewDTO);
    //리뷰 전체 조회(업체상세페이지)
    List<ReviewReq> allReview(Integer bnum);
    //리뷰 조회(마이페이지 내리뷰)
    List<ReviewReq> myReview(String id);
    //리뷰 1개 호출(리뷰수정전 기존리뷰 호출)
    ReviewReq findReview(int rnum);
    //리뷰수정
    List<ReviewReq> updateReview(int bnum, String id, ReviewDTO reviewDTO);
    //리뷰삭제
    List<ReviewReq> removeReview(int bnum, int rnum);
	
}
