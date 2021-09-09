package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.MyAniDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyAniDAOImpl implements MyAniDAO {

	private final JdbcTemplate JdbcTemplate;
	
	//키우는 동물 등록
	@Override
	public void registerMyAni(MyAniDTO myAniDTO) {
		String sql = "insert into MYANI(id,animal,mnum) values(?,?,myani_mnum_seq) ";
		List<String> myani = myAniDTO.getMyAni(); 
		
		for(int i=0; i<myani.size();i++) {
//		  JdbcTemplate.queryForObject(sql,String.class,myAniDTO.getId(),myani[i]);
//		  JdbcTemplate.query(sql,new BeanPropertyRowMapper<>(String.class),myAniDTO.getId(),myani[i]);
//		  JdbcTemplate.queryForList(sql,String.class,myAniDTO.getId(),myani[i]);
//		  JdbcTemplate.queryForList(sql,new BeanPropertyRowMapper<>(String.class),myAniDTO.getId(),myani[i]);
		
		}

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
