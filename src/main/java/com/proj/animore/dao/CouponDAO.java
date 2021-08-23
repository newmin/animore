package com.proj.animore.dao;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.CouponDTO;

public interface CouponDAO {
	
	//쿠폰등록
	void addCoupon(CouponDTO couponDTO);
	
	//쿠폰조회
	CouponDTO findCouponByCid(int Cnum);
	
	//쿠폰삭제(자동만료??)
	void deleteCoupon(int Cnum);
	
}
