package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.MemberDTO;

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

			log.info(memberDTO.getId(),
												memberDTO.getPw(),
												memberDTO.getName(),
												memberDTO.getBirth(),
												memberDTO.getGender(),
												memberDTO.getTel(),
												memberDTO.getEmail(),
												memberDTO.getAddress(),
												memberDTO.getNickname(),
												memberDTO.getMtype());
	}

	@Override
	public MemberDTO findMemberById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberDTO modifyMember(String id, MemberDTO memberDTO) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public void deleteMember(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MemberDTO> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberDTO findByIdPw(String id, String pw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findId(String name, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findPw(String id, String name, String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
