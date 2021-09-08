package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.MypageReplyRes;
import com.proj.animore.dto.board.BoardDTO;
import com.proj.animore.form.ReviewForm;
import com.proj.animore.form.board.BoardForm;

public interface MyPageDAO {
	

	//마일리지 memeber에 마일리지 있음
	
	
	//쿠폰등록 마일리지로 바꾸는것임 일단 쿠폰DAO만 만듬;;
	
	
	//내가 쓴 게시글
	List<BoardForm> myBoard(String id);
	
	
	//내가 쓴 리뷰
	List<ReviewForm> myReview(String id);


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//내가쓴댓글
	List<MypageReplyRes> mypageReply(String id);
}
