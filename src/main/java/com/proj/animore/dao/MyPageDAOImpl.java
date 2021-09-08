package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.MypageReplyRes;
import com.proj.animore.form.ReviewForm;
import com.proj.animore.form.board.BoardForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyPageDAOImpl implements MyPageDAO{
	
	private final JdbcTemplate jdbcTemplate;
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<MypageReplyRes> mypageReply(String id) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.rnum, t1.bnum, t1.rcontent, t1.rcdate, t2.bgood ");
		sql.append("from rboard t1, board t2 ");
		sql.append("where t1.id=? ");
		sql.append("  and t1.bnum=t2.bnum ");
		
		
		List<MypageReplyRes> mypageReplyList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(MypageReplyRes.class),id);
		
		return mypageReplyList;
	}
	
}
