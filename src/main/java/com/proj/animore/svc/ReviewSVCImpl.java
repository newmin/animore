package com.proj.animore.svc;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.proj.animore.dao.ReviewDAO;
import com.proj.animore.form.ReviewForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewSVCImpl implements ReviewSVC {

	private final ReviewDAO reviewDAO;
	
	@Override
	public List<ReviewForm> registReview(@PathVariable int bnum, @PathVariable String id, ReviewForm reviewForm) {
		return reviewDAO.registReview(bnum, id, reviewForm);
	}

	@Override
	public List<ReviewForm> allReview(@PathVariable int bnum) {
		return reviewDAO.allReview(bnum);
	}

	@Override
	public List<ReviewForm> myReview(@PathVariable String id) {
		return reviewDAO.myReview(id);
	}

	@Override
	public List<ReviewForm> updateReview(@PathVariable int bnum, @PathVariable String id, ReviewForm reviewForm) {
		return reviewDAO.updateReview(bnum, id, reviewForm);
	}

	@Override
	public List<ReviewForm> removeReview(@PathVariable int bnum, @PathVariable String id) {
		return reviewDAO.removeReview(bnum, id);
	}

}
