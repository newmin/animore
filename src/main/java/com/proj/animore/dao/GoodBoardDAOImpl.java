package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.GoodBoardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class GoodBoardDAOImpl implements GoodBoardDAO {

	private final JdbcTemplate jt;
	
	//좋아요추가
	@Override
	public void addGoodBoard(String id,Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into goodboard(gnum,id,bnum) ");
		sql.append("  values(goodboard_gnum_seq.nextval, ?, ?) ");
		jt.update(sql.toString(),id,bnum);

		
	}
	//좋아요숫자증가
	@Override
	public void upGoodBoardCnt(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("  set bgood=bgood+1 ");
		sql.append("  where bnum=? ");
		
		jt.update(sql.toString(),bnum);
		
	}
	//좋아요삭제
	@Override
	public void delGoodBoard(Integer bnum, String id) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("delete goodboard ");
		sql.append("where bnum=? ");
		sql.append("and id=? ");
		
		jt.update(sql.toString(),bnum,id);

	}
	//좋아요 전체목록
	@Override
	public List<GoodBoardDTO>goodBoardList(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.id,t2.gnum,t1.bcategory,t1.btitle,t1.bgood ");
		sql.append("from board t1, goodboard t2 ");
		sql.append("where t1.bnum = t2.bnum ");
		sql.append("and t2.id =? ");
		
		List<GoodBoardDTO> goodBoardList = jt.query(sql.toString(), 
										new BeanPropertyRowMapper<>(GoodBoardDTO.class),
										id);
		
		
		return goodBoardList;
	}
	
	//좋아요숫자감소
	@Override
	public void downGoodBoardCnt(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("  set bgood=bgood-1 ");
		sql.append("  where bnum=? ");
		
		jt.update(sql.toString(),bnum);
		
	}
	//해당글 좋아요 했는지 여부
	@Override
	public int isGoodBoard(Integer bnum, String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(t2.id) ");
		sql.append("from board t1, goodboard t2 ");
		sql.append("where t1.bnum = t2.bnum ");
		sql.append("and t2.bnum = ? ");
		sql.append("and t2.id = ? ");
		
		Integer res= jt.queryForObject(sql.toString(), Integer.class, bnum, id);
		return res;
	}
	
	@Override
	public int GoodBoardCnt(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select bgood ");
		sql.append(" from board ");
		sql.append("    where bnum=? " );
		
	return jt.queryForObject(sql.toString(), Integer.class,bnum);
	
	}
}
