package com.proj.animore.dao.business;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.business.BcategoryDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BcategoryDAOImpl implements BcategoryDAO {

	private final JdbcTemplate jdbcTemplate;

	//업체카테고리 등록
	@Override
	public void addBcategory(BcategoryDTO bcategoryDTO) {
		String sql = new String();
		
		Integer bnum = jdbcTemplate.queryForObject("select business_bnum_seq.currval from dual ", Integer.class);
		
		sql = "insert into BCATEGORY values(?,?,?,?,?,?,?,?,?,?,?) ";
		jdbcTemplate.update(sql,bnum,bcategoryDTO.getBhospital(),
				bcategoryDTO.getBpharmacy(),bcategoryDTO.getBhotel(),
				bcategoryDTO.getBkindergarden(),bcategoryDTO.getBfood(),
				bcategoryDTO.getBtraining(),bcategoryDTO.getBshop(),
				bcategoryDTO.getBplayground(),bcategoryDTO.getBhairshop(),
				bcategoryDTO.getBetc());	
	}

	//업체카테고리 삭제
	@Override
	public void delBcategory(String bnum) {
		String sql = "delete from bcategory where bnum = ? ";
		jdbcTemplate.update(sql,bnum);
	}
	
}
