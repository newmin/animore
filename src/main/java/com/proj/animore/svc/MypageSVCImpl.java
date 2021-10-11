package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.MyPageDAO;
import com.proj.animore.dto.MypageReplyRes;
import com.proj.animore.dto.business.ReviewReq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageSVCImpl implements MypageSVC {
	private final MyPageDAO mypageDAO;	
	
	@Override
	public List<MypageReplyRes> mypageReply(String id) {
		return mypageDAO.mypageReply(id);
	}

	@Override
	public List<ReviewReq> mybusiReview(String id) {
		return mypageDAO.mybusiReview(id);
	}
}
