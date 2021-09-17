package com.proj.animore.svc.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proj.animore.common.file.FileStore;
import com.proj.animore.dao.MemberDAO;
import com.proj.animore.dao.business.ReviewDAO;
import com.proj.animore.dao.business.ReviewFileDAO;
import com.proj.animore.dto.business.BusiUploadFileDTO;
import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewSVCImpl implements ReviewSVC {

	private final ReviewDAO reviewDAO;
	private final ReviewFileDAO reviewFileDAO;
	private final MemberDAO memberDAO;
	private final FileStore fileStore;
	
	//리뷰 등록
	@Override
	@Transactional
	public List<ReviewReq> registReview(ReviewDTO reviewDTO) {
		int rnum = reviewDAO.registReview(reviewDTO);
		if(reviewDTO.getFiles() !=null && reviewDTO.getFiles().size() > 0) {		
			reviewFileDAO.registReviewFile(convert(rnum, reviewDTO.getFiles()));
		}
		memberDAO.upMileage(reviewDTO.getId(), 100);
		return allReview(reviewDTO.getBnum());
	}
	
	private List<BusiUploadFileDTO> convert(Integer rnum,List<BusiUploadFileDTO> files){
		for(BusiUploadFileDTO bdto : files) {bdto.setRefer_num(rnum);}
		
		return files;
	}
	
	//업체 조회(모든 리뷰)
	@Override
	public List<ReviewReq> allReview(Integer bnum) {
		List<ReviewReq> list = reviewDAO.allReview(bnum);
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/review/");
		for(int i=0; i<list.size(); i++) {
			list.get(i).setFiles(reviewFileDAO.getReviewFiles(list.get(i).getRnum()));
		}
		return list;
	}

	//내가 쓴 리뷰(마이페이지)
	@Override
	public List<ReviewReq> myReview(String id) {
	  List<ReviewReq> list = reviewDAO.myReview(id);
	  for(int i=0; i<list.size(); i++) {
		list.get(i).setFiles(reviewFileDAO.getReviewFiles(list.get(i).getRnum())); }
	  return list;
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
		reviewDAO.updateReview(reviewDTO);
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/review/");
		if(reviewDTO.getFiles() !=null && reviewDTO.getFiles().size() > 0) {	
		 reviewFileDAO.updateReviewFiles(convert(reviewDTO.getRnum(), reviewDTO.getFiles())); 
		}
//		 reviewFileDAO.registReviewFile(reviewDTO.getFiles()); 
		return allReview(reviewDTO.getBnum());
	}

	//리뷰 삭제
	@Override
	public List<ReviewReq> removeReview(int bnum, int rnum) {
		reviewDAO.removeReview(rnum);
		reviewFileDAO.removeReviewFiles(rnum);
		return allReview(bnum);
	}
	//리뷰파일낱개삭제
	@Override
	public ReviewReq delReviewImg(int rnum,int fnum) {
		reviewFileDAO.removeEachFile(fnum);
		fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/review/");
		return findReview(rnum);
	}
	
	
	@Override
	public ReviewReq findRvReply(int rnum) {
		return reviewDAO.findRvReply(rnum);
	}
	//사장님 답글 등록
	@Override
	public List<ReviewReq> addRvReply(ReviewReq reviewReq) {
		reviewDAO.addRvReply(reviewReq);
		return allReview(reviewReq.getBnum());
	}
    //사장님 리뷰리댓 삭제
	@Override
	public List<ReviewReq> delRvReply(int bnum, int rnum) {
		reviewDAO.delRvReply(rnum);
		return allReview(bnum);
	}

}
