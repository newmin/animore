package com.proj.animore.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.MyAniDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyAniDAOImpl implements MyAniDAO {

	private final JdbcTemplate jdbcTemplate;
	
	//키우는 동물 등록
	@Override
	public void registerMyAni(MyAniDTO myAniDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into myani(mnum,id,animal) values(myani_mnum_seq,?,?) ");

		jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, myAniDTO.getId());
				ps.setString(2, myAniDTO.getMyAni().get(i));
			}

			@Override
			public int getBatchSize() {
				return myAniDTO.getMyAni().size();
			}
		});//batchUpdate

	}

	//키우는 동물 조회
	@Override
	public List<String> findMyAni(String id) {
		StringBuffer sql = new StringBuffer();
		
		return null;
	}

	//키우는 동물 삭제
	@Override
	public void lostMyAni(String id) {
		// TODO Auto-generated method stub

	}

}
