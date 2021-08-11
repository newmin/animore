package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.BusinessDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BusinessDAOImpl implements BusinessDAO {

	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public void joinBusi(BusinessDTO businessDTO) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into business(bnum, bbnum, bid, bname, baddress, btel, openhours) values(business_bnum_seq.nextval,?,?,?,?,?,?) ");
		
		jdbcTemplate.update(sql.toString(),
												businessDTO.getBbnum(),
												businessDTO.getBid(),
												businessDTO.getBname(),
												businessDTO.getBaddress(),
												businessDTO.getBtel(),
												businessDTO.getOpenhours());
		
	}

	@Override
	public BusinessDTO findBusiByBbnum(String bnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessDTO modifyBusi(String bnum, BusinessDTO businessDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBusi(String bnum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BusinessDTO> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
