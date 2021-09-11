package com.proj.animore.svc.business;

import java.util.List;

import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

public interface ReviewSVC {

    //리뷰등록
	List<ReviewReq> registReview(ReviewDTO reviewDTO);
    //리뷰 전체 조회(업체상세페이지)
    List<ReviewReq> allReview(Integer bnum);
    //리뷰 조회(마이페이지 내리뷰리스트)
    List<ReviewReq> myReview(String id);
    //리뷰 1개 호출(리뷰수정전 기존리뷰 호출)
    ReviewReq findReview(int rnum);
    //리뷰수정
    List<ReviewReq> updateReview(ReviewDTO reviewDTO);
    //리뷰삭제
    List<ReviewReq> removeReview(int bnum, int rnum);
    //사장님 이전 리뷰리댓 조회
    ReviewReq findRvReply(int rnum);
    //사장님 리뷰리댓등록
    List<ReviewReq> addRvReply(ReviewReq reviewReq);
}
