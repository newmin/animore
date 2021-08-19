package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
	//업체등록
	@Override
	public void joinBusi(BusinessDTO businessDTO) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into business(bnum, bbnum, id, bname, baddress, btel, openhours) values(business_bnum_seq.nextval,?,?,?,?,?,?) ");
		
		jdbcTemplate.update(sql.toString(),
												businessDTO.getBbnum(),
												businessDTO.getId(),
												businessDTO.getBname(),
												businessDTO.getBaddress(),
												businessDTO.getBtel(),
												businessDTO.getOpenhours());
		
	}
	//업체찾기
	@Override
	public BusinessDTO findBusiByBbnum(String bnum) {
		StringBuffer sql = new StringBuffer();
		//sql문
		sql.append("select bunm,bname,");
		sql.append("from business");
		sql.append("where bnum = ? ");
		BusinessDTO businessDTO = jdbcTemplate.queryForObject(sql.toString(),
				new BeanPropertyRowMapper<>(BusinessDTO.class),
				bnum);
		
		
		return businessDTO;
	}

	@Override
	public BusinessDTO modifyBusi(String bnum, BusinessDTO businessDTO) {
		StringBuilder sql = new StringBuilder();
		sql.append("update business ");
		sql.append("   set bname = ?, ");
		sql.append("       baddress = ?, ");
		sql.append("       btel = ? ");
		sql.append(" where bnm = ? ");
		
		jdbcTemplate.update(
				sql.toString(), 
				businessDTO.getBname(),
				businessDTO.getBaddress(),
				businessDTO.getBtel()
				);
		return findBusiByBbnum(bnum);
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
	//업체추가
	public void addBusi(BusinessDTO businessDTO) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into business(bnum, bbnum, id, bname, baddress, btel, openhours) values(business_bnum_seq.nextval,?,?,?,?,?,?) ");
		
		jdbcTemplate.update(sql.toString(),
												businessDTO.getBbnum(),
												businessDTO.getId(),
												businessDTO.getBname(),
												businessDTO.getBaddress(),
												businessDTO.getBtel(),
												businessDTO.getOpenhours());
		
	}
	
	
}
