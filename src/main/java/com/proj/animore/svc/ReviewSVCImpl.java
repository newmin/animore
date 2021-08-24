package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.ReviewDAO;
import com.proj.animore.dto.ReviewReq;
import com.proj.animore.form.ReviewForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewSVCImpl implements ReviewSVC {

	private final ReviewDAO reviewDAO;
	
	@Override
	public List<ReviewReq> registReview(Integer bnum, String id, ReviewForm reviewForm) {
		return reviewDAO.registReview(bnum, id, reviewForm);
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
	public List<ReviewReq> updateReview(Integer bnum, String id, ReviewForm reviewForm) {
		return reviewDAO.updateReview(bnum, id, reviewForm);
	}

	@Override
	public List<ReviewReq> removeReview(Integer bnum, String id) {
		return reviewDAO.removeReview(bnum, id);
	}

}
