package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.ReviewDAO;
import com.proj.animore.dto.ReviewDTO;
import com.proj.animore.dto.ReviewReq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewSVCImpl implements ReviewSVC {

	private final ReviewDAO reviewDAO;
	
	@Override
	public List<ReviewReq> registReview(Integer bnum, String id, ReviewDTO reviewDTO) {
		return reviewDAO.registReview(bnum, id, reviewDTO);
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
	public List<ReviewReq> updateReview(int bnum, String id, ReviewDTO reviewDTO) {
		return reviewDAO.updateReview(bnum, id, reviewDTO);
	}

	@Override
	public List<ReviewReq> removeReview(int bnum, int rnum, String id) {
		return reviewDAO.removeReview(bnum, rnum, id);
	}

}
