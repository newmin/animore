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
	public void addFavorite(FavoriteDTO FavoriteDTO) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into favorite() values(Favorite_mnum_seq.nextval,?,?,?) ");
		
		jdbcTemplate.update(sql.toString(),
											FavoriteDTO.getId(),
											FavoriteDTO.getBnum(),
											FavoriteDTO.getMnum()
											);
		
	}
	//즐겨찾기 삭제
	@Override
	public void deleteFavorite(Integer mnum) {
		String sql = " delete Favorite where Mnum=? ";
		jdbcTemplate.update(sql.toString(),mnum);
		
	}
	//즐겨찾기 목록
	@Override
	public List<FavoriteDTO> list(Integer mnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select business.bname,business.bname baddress  ");
		sql.append(" from favorite,business " );
		sql.append("  where  ");
		sql.append("   and  ");
		List<FavoriteDTO> list = jdbcTemplate.query(sql.toString(),
										new BeanPropertyRowMapper<>(FavoriteDTO.class),
										mnum);
		return list;
	}
}
