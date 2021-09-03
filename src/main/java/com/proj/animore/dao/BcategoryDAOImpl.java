package com.proj.animore.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.BcategoryDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BcategoryDAOImpl implements BcategoryDAO {

	private final JdbcTemplate jdbcTemplate;

	
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

}
