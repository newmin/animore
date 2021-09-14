package com.proj.animore.svc.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proj.animore.dao.business.ReviewDAO;
import com.proj.animore.dao.business.ReviewFileDAO;
import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//@Transactional
public class ReviewSVCImpl implements ReviewSVC {

	private final ReviewDAO reviewDAO;
	private final ReviewFileDAO reviewFileDAO;
	
	//리뷰 등록
	@Override
	public List<ReviewReq> registReview(ReviewDTO reviewDTO) {
		int rnum = reviewDAO.registReview(reviewDTO);
		reviewFileDAO.registReviewFile(rnum, reviewDTO.getFiles());
		return reviewDAO.allReview(reviewDTO.getBnum());
	}
	
	//업체 조회(모든 리뷰)
	@Override
	public List<ReviewReq> allReview(Integer bnum) {
		return reviewDAO.allReview(bnum);
	}

	//내가 쓴 리뷰(마이페이지)
	@Override
	public List<ReviewReq> myReview(String id) {
		return reviewDAO.myReview(id);
	}
	
	//리뷰 1개 조회(수정시 호출)
	@Override
	public ReviewReq findReview(int rnum) {
		ReviewReq review = reviewDAO.findReview(rnum);
		review.setFiles(reviewFileDAO.getReviewFiles(rnum));
		return review;
	}

	//리뷰 수정
	@Override
	public List<ReviewReq> updateReview(ReviewDTO reviewDTO) {
		return reviewDAO.updateReview(reviewDTO);
	}

	//리뷰 삭제
	@Override
	public List<ReviewReq> removeReview(int bnum, int rnum) {
		reviewFileDAO.removeReviewFiles(rnum);
		return reviewDAO.removeReview(bnum, rnum);
	}
	
	
	@Override
	public ReviewReq findRvReply(int rnum) {
		return reviewDAO.findRvReply(rnum);
	}
	
	@Override
	public List<ReviewReq> addRvReply(ReviewReq reviewReq) {
		return reviewDAO.addRvReply(reviewReq);
	}
    //사장님 리뷰리댓 삭제
	@Override
	public List<ReviewReq> delRvReply(int bnum, int rnum) {
		reviewDAO.delRvReply(rnum);
		return reviewDAO.allReview(bnum);
	}

}
