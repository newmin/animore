package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.ReviewReq;
import com.proj.animore.form.ReviewForm;

public interface ReviewDAO {

	//리뷰등록
	List<ReviewReq> registReview(Integer bnum, String id, ReviewForm reviewForm);
    //리뷰 전체 조회(업체상세페이지)
    List<ReviewReq> allReview(Integer bnum);
    //리뷰 조회(마이페이지 내리뷰리스)
    List<ReviewReq> myReview(String id);
    //리뷰수정
    List<ReviewReq> updateReview(Integer bnum, String id, ReviewForm reviewForm);
    //리뷰삭제
    List<ReviewReq> removeReview( Integer bnum, String id);
	
}
