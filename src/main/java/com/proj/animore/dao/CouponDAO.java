package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.CouponDTO;
import com.proj.animore.dto.board.BoardDTO;

public interface CouponDAO {
	
	//쿠폰등록
	void addCoupon(CouponDTO couponDTO);
	
	//쿠폰조회
	List<CouponDTO> findCouponById(String id);
	
	//쿠폰삭제(자동만료??)
	void deleteCoupon(int cnum);
	
}
