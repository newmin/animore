package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.BoardReqDTO;
import com.proj.animore.dto.FavoriteDTO;

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
		
		sql = "insert into favorite values(Favorite_mnum_seq.nextval,?,?) ";
		
		jdbcTemplate.update(sql,bnum,id);
		
	}
	//즐겨찾기 삭제
	@Override
	public void deleteFavorite(Integer bnum, String id) {
		String sql = " delete from favorite where bnum=? and id=? ";
		jdbcTemplate.update(sql,bnum,id);
		
	}
	//즐겨찾기 목록
	@Override
	public List<FavoriteDTO>favoriteList(String id) {

		StringBuffer sql = new StringBuffer();
		sql.append(" select f.mnum , b.bname , m.id, b.bscore ");
		sql.append("   from favorite f, business b ,member m " );
		sql.append("  where f.bnum = b.bnum  ");
		sql.append("    and f.id = m.id" );
		sql.append("    and m.id = ? " );

		List<FavoriteDTO> favoritelist = jdbcTemplate.query(sql.toString(),
										new BeanPropertyRowMapper<>(FavoriteDTO.class),
										id);
		log.info(favoritelist.toString());
		
		
		return favoritelist;
	}
}
