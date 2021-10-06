package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.CouponDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CouponDAOImpl implements CouponDAO{
	
	private final JdbcTemplate jt;
	
	//쿠폰등록
	@Override
	public void addCoupon(CouponDTO couponDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into Coupon(cnum,id,price,cflag) ");
		sql.append("   values(coupon_cnum_seq.nextval,?,?,?) ");
		jt.update(sql.toString(),
				couponDTO.getId(),
				couponDTO.getPrice(),
				couponDTO.getCflag()
				);
	
	log.info("boardDTO:{}",couponDTO.toString());
		
	}
	//쿠폰조회
	@Override
	public List<CouponDTO> findCouponById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select cnum,id,price,cflag ");
		sql.append("from coupon ");
		sql.append("where id=? ");
		sql.append("and cflag='Y' ");
		
		List<CouponDTO> list = jt.query(sql.toString(),
				new BeanPropertyRowMapper<>(CouponDTO.class),
				id);

		return list;
	}
	//쿠폰삭제(만료)
	//쿠폰사용
	@Override
	public void deleteCoupon(int cnum) {
		String sql ="update coupon set cflag='N' where cnum=?";
		jt.update(sql.toString(),cnum);
		
	}
	
	

}
