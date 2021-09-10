package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.business.FavoriteReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class FavoriteDAOImpl implements FavoriteDAO{
	
	private final JdbcTemplate jdbcTemplate;
	
	//즐겨찾기 추가
	@Override
	public void addFavorite(Integer bnum, String id) {
		String sql = new String();
		
		sql = "insert into favorite values(Favorite_fnum_seq.nextval,?,?,systimestamp) ";
		
		jdbcTemplate.update(sql,bnum,id);
		
	}
	//즐겨찾기 삭제
	@Override
	public void deleteFavorite(Integer bnum, String id) {
		StringBuffer sql = new StringBuffer(); 
		sql.append(" delete from favorite ");
		sql.append(" where bnum=? ");
		sql.append(" and id= ? ");
		jdbcTemplate.update(sql.toString(),bnum,id);
		
	}
	//즐겨찾기 목록
	@Override
	public List<FavoriteReq>favoriteList(String id) {

//		Integer avg = jdbcTemplate.queryForObject("select avg(rscore) from review where bnum = ? ",Integer.class,bnum);
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select f.id id,b.bname bname,f.bnum, r.avg bscore, fnum, fdate ");
		sql.append("  from favorite f, business b,(select bnum, round(avg(rscore),2) avg ");
		sql.append("                                from review ");
		sql.append("                                group by bnum) r ");
		sql.append(" where f.bnum=b.bnum ");
		sql.append("   and f.bnum=r.bnum ");
		sql.append("   and f.id= ? ");
		sql.append("order by fdate desc");

		List<FavoriteReq> favoritelist = jdbcTemplate.query(sql.toString(),
										new BeanPropertyRowMapper<>(FavoriteReq.class),
										id);
		log.info(favoritelist.toString());
		
		
		return favoritelist;
	}
	
	//즐겨찾기 여부 조회
	@Override
	public FavoriteReq isFavorite(Integer bnum, String id) {
//		public int isFavorite(Integer bnum, String id) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(fnum) count");
		sql.append("  from favorite ");
		sql.append(" where bnum = ? ");
		sql.append("   and id = ? ");
		
		FavoriteReq favor = jdbcTemplate.queryForObject(sql.toString(),
								new BeanPropertyRowMapper<>(FavoriteReq.class),bnum,id);
		return favor;
	}
	
	
	
	
}
