package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.MemberDTO;
import com.proj.animore.form.FindIdForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberDAOImpl implements MemberDAO {

	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public void joinMember(MemberDTO memberDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into member(id,pw,name,birth,gender,tel,email,address,nickname,mtype) ");
		sql.append(" values(?,?,?,?,?,?,?,?,?,?) ");
		
		jdbcTemplate.update(sql.toString(),
												memberDTO.getId(),
												memberDTO.getPw(),
												memberDTO.getName(),
												memberDTO.getBirth(),
												memberDTO.getGender(),
												memberDTO.getTel(),
												memberDTO.getEmail(),
												memberDTO.getAddress(),
												memberDTO.getNickname(),
												memberDTO.getMtype());

			log.info("memberDTO : {}", memberDTO.toString());
	}

	@Override
	public MemberDTO findMemberById(String id) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id,pw,name,birth,gender,tel,email,address,nickname ");
		sql.append("from member ");
		sql.append("where id= ? ");

		MemberDTO memberDTO = jdbcTemplate.queryForObject(sql.toString(), 
																	new BeanPropertyRowMapper<>(MemberDTO.class),
																	id);
		return memberDTO;
	}

	@Override
	public MemberDTO modifyMember(String id, MemberDTO memberDTO) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("update member ");
		sql.append("set pw = ? ");
		sql.append("    name = ? ");
		sql.append("    birth = ? ");
		sql.append("    gender = ? ");
		sql.append("    tel = ? ");
		sql.append("    email = ? ");
		sql.append("    address = ? ");
		sql.append("    nickname = ? ");
		sql.append("where id = ? ");

		jdbcTemplate.update(sql.toString(),
												memberDTO.getPw(),
												memberDTO.getName(),
												memberDTO.getBirth(),
												memberDTO.getGender(),
												memberDTO.getTel(),
												memberDTO.getEmail(),
												memberDTO.getAddress(),
												memberDTO.getNickname(),
												id);
		
		return findMemberById(id);

	}

	@Override
	public void deleteMember(String id) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("delete from member ");
		sql.append("where id = ? ");
		
		jdbcTemplate.update(sql.toString(),
												id);

	}

	@Override
	public List<MemberDTO> list() {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id,pw,name,birth,gender,tel,email,address,nickname ");
		sql.append("from member ");
		
		List<MemberDTO> list = jdbcTemplate.query(sql.toString(),
																							new BeanPropertyRowMapper<>(MemberDTO.class));
		return list;
	}


	@Override 
	//TODO 파라미터가 memberDTO(or name,email만의 폼)가 되고 getter로 가져오기?
	public List<FindIdForm> findId(FindIdForm findIdForm) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id ");
		sql.append("from member ");
		sql.append("where name = ? ");
		sql.append("  and email= ? ");
		
		List<FindIdForm> id =  jdbcTemplate.query(sql.toString(),
												new BeanPropertyRowMapper<>(FindIdForm.class),
												findIdForm.getName(),findIdForm.getEmail());
		
		log.info("form:{}",id.toString());
//		log.info("id={}",id.getId());
		return id;
	}

	@Override
	public String findPw(String id, String name, String email) {
StringBuffer sql = new StringBuffer();
		
		sql.append("select pw ");
		sql.append("from member ");
		sql.append("where id = ? ");
		sql.append("      name= ? ");
		sql.append("      email= ? ");

		
		return null;
	}

	@Override
	   public MemberDTO findByIdPw(String id, String pw) {

	      StringBuffer sql = new StringBuffer();

	      sql.append("select id, mtype, nickname ");
	      sql.append("from member ");
	      sql.append("where id = ? ");
	      sql.append("  and pw= ? ");
	      
	     MemberDTO memberDTO = jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(MemberDTO.class),id,pw);
	      return memberDTO;

}
}