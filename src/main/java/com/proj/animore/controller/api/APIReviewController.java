package com.proj.animore.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.animore.common.file.FileStore;
import com.proj.animore.common.file.MetaOfUploadFile;
import com.proj.animore.dto.business.BusiUploadFileDTO;
import com.proj.animore.dto.business.BusinessLoadDTO;
import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;
import com.proj.animore.form.LoginMember;
import com.proj.animore.form.Result;
import com.proj.animore.form.ReviewForm;
import com.proj.animore.svc.business.BusinessSVC;
import com.proj.animore.svc.business.ReviewSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
@Transactional
public class APIReviewController {
	
	private final ReviewSVC reviewSVC;
	private final BusinessSVC businessSVC;
	private final FileStore fileStore;

	//리뷰등록
	@PostMapping("/")
	public Result addReview(
			@ModelAttribute ReviewForm reviewForm, 
			HttpServletRequest request) throws IllegalStateException, IOException{
		
		Result result;
		
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loginMember") == null) {
			result = new Result("01","로그인이 만료되었어요 다시 로그인해주세요.",null);
			return result;
		}
				
		//리뷰작성폼→리뷰DTO
		ReviewDTO reviewDTO = new ReviewDTO();
		BeanUtils.copyProperties(reviewForm,reviewDTO);
				
		//첨부파일 등록
		if(reviewForm.getFiles() !=null && reviewForm.getFiles().size() > 0) {			
			fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/review/");
			List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(reviewForm.getFiles());
			reviewDTO.setFiles(convert(storedFiles));
		}
		
		List<ReviewReq> list = reviewSVC.registReview(reviewDTO);
		
		result = new Result("00","성공",list);
	 	return result;
	}
	//메타정보 → 업로드 정보
	private BusiUploadFileDTO convert(MetaOfUploadFile attatchFile) {
		BusiUploadFileDTO uploadFileDTO = new BusiUploadFileDTO();
		BeanUtils.copyProperties(attatchFile, uploadFileDTO);
		return uploadFileDTO;
	}
	
	//메타정보 → 업로드 정보
	private List<BusiUploadFileDTO> convert(List<MetaOfUploadFile> uploadFiles) {
		List<BusiUploadFileDTO> list = new ArrayList<>();

		for(MetaOfUploadFile file : uploadFiles) {
			BusiUploadFileDTO uploadFIleDTO = convert(file);
			list.add( uploadFIleDTO );
		}		
		return list;
	}
	
	
	// //리뷰1개 호출(리뷰수정폼)
	@GetMapping("/")
	public Result findReview(@RequestParam int rnum,
							 HttpServletRequest request) {
		Result result;											
		//로그인x
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loginMember")==null){
			result = new Result("01","로그인이 만료되었습니다.",null);
			return result;
		}												

		//아이디값 다른 경우
		 LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		 String userId = loginMember.getId(); //사용자ID
		 
		 ReviewReq reviewReq	= reviewSVC.findReview(rnum);
		 String rid = reviewReq.getId();

		 if(!rid.equals(userId)){
		 	result = new Result("02","작성자의 아이디와 일치하지 않습니다.",null);
		 	return result;
		 }

		result = new Result("00","성공",reviewReq);
		return result;
	}
	
	 //리뷰수정
	@PatchMapping("/")
	public Result modiReview(@ModelAttribute ReviewForm reviewForm, 
							HttpServletRequest request) throws IllegalStateException, IOException{
		
		log.info("reviewForm:{}",reviewForm);
		Result result;
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("loginMember") == null) {
			result = new Result("01","로그인이 만료되었어요. 다시 로그인해주세요.",null);
			return result;
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String userId = loginMember.getId(); //사용자ID
		
		ReviewReq review = reviewSVC.findReview(reviewForm.getRnum());
		String rid = review.getId();
		
		if(!userId.equals(rid)){
			result = new Result("02","해당 리뷰작성자와의 아이디가 일치하지 않습니다.",null);
			return result;
		}
		
		//리뷰작성폼→리뷰DTO
		//리뷰작성폼→리뷰DTO
		ReviewDTO reviewDTO = new ReviewDTO();
		BeanUtils.copyProperties(reviewForm,reviewDTO);
				
		//첨부파일 등록
		if(reviewForm.getFiles() !=null && reviewForm.getFiles().size() > 0) {	
			fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/review/");
			List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(reviewForm.getFiles());
			reviewDTO.setFiles(convert(storedFiles));
		}
		List<ReviewReq> list = reviewSVC.updateReview(reviewDTO);
		result = new Result("00","성공",list);
		return result;
	}
	
	//리뷰삭제
	@DeleteMapping("/")
	public Result deleteReview(@RequestParam int rnum,
							 HttpServletRequest request) {
		Result result;
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loginMember") == null) {
			result = new Result("01","로그인이 만료되었어요. 다시 로그인해주세요.",null);
			return result;
		}
		//로그인회원ID과 리뷰작성자ID가 일치하지 않을 경우
		 LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		 String userId = loginMember.getId(); //사용자ID

		 ReviewReq review = reviewSVC.findReview(rnum);
		 String rid = review.getId();
		 
		 if(!userId.equals(rid)){
		 	result = new Result("01","해당 리뷰작성자와의 아이디가 일치하지 않습니다.",null);
		 	return result;
		 }
		
		List<ReviewReq> list = reviewSVC.removeReview(review.getBnum(), rnum);
		result = new Result("00","성공",list);
		return result;
	}
//리뷰첨부파일 삭제
@DeleteMapping("/del")
public Result deleteReviewAttach(@RequestParam int fnum,
								@RequestParam int rnum,
								 HttpServletRequest request) {
	Result result;
	HttpSession session = request.getSession(false);
	if(session == null || session.getAttribute("loginMember") == null) {
	result = new Result("01","로그인이 만료되었어요. 다시 로그인해주세요.",null);
	return result;
	}
	fileStore.setFilePath("D:/animore/src/main/resources/static/img/upload/review/");
	ReviewReq reviewReq = reviewSVC.delReviewImg(rnum,fnum);
	result = new Result("00","성공",reviewReq);
	return result;
}
	
	//사장님 리뷰리댓 등록/수정폼
	@GetMapping("/reply")
	public Result rvReplyForm(@RequestParam int rnum, 
							 HttpServletRequest request) {
		Result result;
		//로그인 확인
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loginMember")==null) {
			result = new Result("01","로그인이 만료되었습니다.",null);
			return result;
		}
		
		//아이디 일치 여부
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String userId = loginMember.getId(); //사용자ID

		ReviewReq reviewReq = reviewSVC.findRvReply(rnum);
		int bnum = reviewReq.getBnum();
		BusinessLoadDTO business = businessSVC.findBusiByBnum(bnum);
		String bid = business.getId(); //사업자ID
		
		if(!userId.equals(bid)) {
			result = new Result("02","해당 업체의 사업자 아이디와 일치하지 않습니다.",null);
		}
		
		result = new Result("00","성공",reviewReq);
		return result;
	}
	
	//사장님 리뷰리댓 등록
	@PatchMapping("/reply")
	public Result addRvReply(@RequestBody ReviewReq reviewReq,
							 HttpServletRequest request) {
		Result result;
		//로그인 확인
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loginMember")==null) {
			result = new Result("01","로그인이 만료되었습니다.",null);
			return result;
		}
		//아이디 일치 여부
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String userId = loginMember.getId(); //사용자ID
		
		int rnum = reviewReq.getRnum();
		ReviewReq review = reviewSVC.findRvReply(rnum);
		int bnum = review.getBnum();
		BusinessLoadDTO business = businessSVC.findBusiByBnum(bnum);
		String bid = business.getId(); //사업자ID
		
		if(!userId.equals(bid)) {
			result = new Result("02","해당 업체의 사업자 아이디와 일치하지 않습니다.",null);
		}
		
		List<ReviewReq> list = reviewSVC.addRvReply(reviewReq);
		result = new Result("00","성공",list);
		return result;
	}
	
	//사장님 리뷰리댓 삭제
	@PatchMapping("/reply/del")
	public Result delRvReply(@RequestBody ReviewReq reviewReq,
							 HttpServletRequest request) {
		Result result;
		//로그인 확인
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loginMember")==null) {
			result = new Result("01","로그인이 만료되었습니다.",null);
			return result;
		}
		//아이디 일치 여부
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		String userId = loginMember.getId();
		
		int rnum = reviewReq.getRnum();
		ReviewReq review = reviewSVC.findRvReply(rnum);
		int bnum = review.getBnum();
		BusinessLoadDTO business = businessSVC.findBusiByBnum(bnum);
		String bid = business.getId(); //사업자ID
		
		if(!userId.equals(bid)) {
			result = new Result("02","해당 업체의 사업자 아이디와 일치하지 않습니다.",null);
		}
		
		List<ReviewReq> list = reviewSVC.delRvReply(reviewReq.getBnum(),reviewReq.getRnum());
		result = new Result("00","성공",list);
		return result;
	}
	
}
