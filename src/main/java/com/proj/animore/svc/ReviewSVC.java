package com.proj.animore.svc;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.proj.animore.form.ReviewForm;

public interface ReviewSVC {

    //리뷰등록
	List<ReviewForm> registReview(int bnum, String id, ReviewForm reviewForm);
    //리뷰 전체 조회(업체상세페이지)
    List<ReviewForm> allReview(int bnum);
    //리뷰 조회(마이페이지 내리뷰리스트)
    List<ReviewForm> myReview(String id);
    //리뷰수정
    List<ReviewForm> updateReview(int bnum, String id, ReviewForm reviewForm);
    //리뷰삭제
    List<ReviewForm> removeReview(int bnum, String id);
}
