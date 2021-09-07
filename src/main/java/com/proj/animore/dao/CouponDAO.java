package com.proj.animore.dao;

import com.proj.animore.dto.CouponDTO;
import com.proj.animore.dto.board.BoardDTO;

public interface CouponDAO {
	
	//쿠폰등록
	void addCoupon(CouponDTO couponDTO);
	
	//쿠폰조회
	CouponDTO findCouponByCid(int cnum);
	
	//쿠폰삭제(자동만료??)
	void deleteCoupon(int cnum);
	
}
