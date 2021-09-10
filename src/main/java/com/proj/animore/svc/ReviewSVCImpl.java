package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.ReviewDAO;
import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

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
	public ReviewReq findReview(int rnum) {
		return reviewDAO.findReview(rnum);
	}

	@Override
	public List<ReviewReq> updateReview(int bnum, String id, ReviewDTO reviewDTO) {
		return reviewDAO.updateReview(bnum, id, reviewDTO);
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
	public List<ReviewReq> addRvReply(int bnum, int rnum, String rvReply) {
		return reviewDAO.addRvReply(bnum, rnum, rvReply);
	}

}
