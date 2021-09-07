package com.proj.animore.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.CouponDTO;
import com.proj.animore.dto.board.BoardDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CouponDAOImpl implements CouponDAO{
	
	JdbcTemplate jt;
	
	//쿠폰등록
	@Override
	public void addCoupon(CouponDTO couponDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into Coupon(cnum,cid,price,cflag) ");
		sql.append("   values(coupon_cnum_seq.nextval,?,?,?,?) ");
		jt.update(sql.toString(),
				couponDTO.getCnum(),
				couponDTO.getCid(),
				couponDTO.getPrice(),
				couponDTO.getCflage()
				);
	
	log.info("boardDTO:{}",couponDTO.toString());
		
	}
	//쿠폰조회
	@Override
	public CouponDTO findCouponByCid(int cnum) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select cnm,cid,price ");
		sql.append("from coupon ");
		sql.append("where cnum=?");
		
		CouponDTO couponDTO = jt.queryForObject(sql.toString(),
				new BeanPropertyRowMapper<>(CouponDTO.class),
				cnum);
		

		return couponDTO;
	}
	//쿠폰석제(만료)
	//쿠폰사용
	@Override
	public void deleteCoupon(int cnum) {
		String sql ="delete coupon where bnum=?";
		jt.update(sql.toString(),cnum);
		
	}
	
	

}
