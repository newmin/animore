package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {

	private final JdbcTemplate jt;
	
	@Override
	public void joinMember(MemberDTO memberDTO) {
		// TODO Auto-generated method stub

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
