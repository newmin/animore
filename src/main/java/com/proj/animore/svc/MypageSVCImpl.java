package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.CouponDAO;
import com.proj.animore.dao.MyPageDAO;
import com.proj.animore.dto.CouponDTO;
import com.proj.animore.dto.MypageReplyRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageSVCImpl implements MypageSVC {
	private final MyPageDAO mypageDAO;	
	private final CouponDAO couponDAO;
	
	@Override
	public List<MypageReplyRes> mypageReply(String id) {
		return mypageDAO.mypageReply(id);
	}
	
	@Override
	public List<CouponDTO> findCouponById(String id) {

		return couponDAO.findCouponById(id);
	}
}
