package com.proj.animore.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.proj.animore.dto.CouponDTO;

public class CouponDAOImpl implements CouponDAO{
	JdbcTemplate jt;
	@Override
	public void addCoupon(CouponDTO couponDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into Coupon(cnum,cid,price,cflag) ");
		sql.append("   values(coupon_cnum_seq.nextval,?,?,?,?) ");
		
	}

	@Override
	public CouponDTO findCouponByCid(int Cnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCoupon(int Cnum) {
		// TODO Auto-generated method stub
		
	}
	
	

}
