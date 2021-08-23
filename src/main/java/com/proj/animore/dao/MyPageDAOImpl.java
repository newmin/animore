package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.proj.animore.dto.MemberDTO;
import com.proj.animore.form.BoardForm;
import com.proj.animore.form.ReviewForm;

public class MyPageDAOImpl implements MyPageDAO{
	
	private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	//내가 쓴 리뷰보기
	@Override
	public List<ReviewForm> myReview(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * ");
		sql.append("  from review ");
		sql.append(" where = '세션에서 받아온 아이디' ");

		List<ReviewForm> list = jdbcTemplate.query(sql.toString(), 
								new BeanPropertyRowMapper<>(ReviewForm.class), 
								id);
	
	
		return list;
	}
	//내가 쓴 게시글 보기
	@Override
	public List<BoardForm> myBoard(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	//내 정보 수정
	
	//회원탈퇴
}
