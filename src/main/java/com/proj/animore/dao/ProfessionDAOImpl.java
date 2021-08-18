package com.proj.animore.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.ProfessionDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@AllArgsConstructor
public class ProfessionDAOImpl implements ProfessionDAO {
	JdbcTemplate jt;
	
	//전문가등록
	@Override
	public void addprofession(ProfessionDTO professionDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into profession (pnum,id,licenseno) ");
		sql.append("  values (profession_pnum_seq.nextval,?,?) ");
		jt.update(sql.toString(),
				professionDTO.getId(),
				professionDTO.getLicenseno());
		
	}
	
	//전문가조회
	@Override
	public ProfessionDTO findProByPnum(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select pnum,id,licenseno ");
		sql.append("from profession ");
		sql.append("where id=? ");
		ProfessionDTO professionDTO = jt.queryForObject(sql.toString(), 
						new BeanPropertyRowMapper<>(ProfessionDTO.class),
						id);
		return professionDTO;
	}
	//전문가수정

	@Override
	public ProfessionDTO modifyProBypnum(String id,ProfessionDTO professionDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("update profession ");
		sql.append("set licenseno=? ");
		sql.append("where id=? ");
		
		jt.update(sql.toString(),
						professionDTO.getLicenseno(),
						id);
		return findProByPnum(id);
	}
	//전문가삭제
	@Override
	public void deleteProBypnum(String id) {
		String sql =" delete profession where id=?";
		jt.update(sql.toString(),id);
		
	}

}
