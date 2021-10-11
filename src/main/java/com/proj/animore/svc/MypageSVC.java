package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dto.MypageReplyRes;
import com.proj.animore.dto.business.ReviewReq;

public interface MypageSVC {

	//내가쓴댓글
	List<MypageReplyRes> mypageReply(String id);
	
	List<ReviewReq> mybusiReview(String id);
	
}
