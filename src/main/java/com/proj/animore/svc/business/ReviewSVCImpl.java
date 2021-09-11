package com.proj.animore.svc.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.business.ReviewDAO;
import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewSVCImpl implements ReviewSVC {

	private final ReviewDAO reviewDAO;
	
	@Override
	public List<ReviewReq> registReview(ReviewDTO reviewDTO) {
		return reviewDAO.registReview(reviewDTO);
	}

	@Override
	public List<ReviewReq> allReview(Integer bnum) {
		return reviewDAO.allReview(bnum);
	}

	@Override
	public List<ReviewReq> myReview(String id) {
		return reviewDAO.myReview(id);
	}
	
	@Override
	public ReviewReq findReview(int rnum) {
		return reviewDAO.findReview(rnum);
	}

	@Override
	public List<ReviewReq> updateReview(ReviewDTO reviewDTO) {
		return reviewDAO.updateReview(reviewDTO);
	}

	@Override
	public List<ReviewReq> removeReview(int bnum, int rnum) {
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

}
