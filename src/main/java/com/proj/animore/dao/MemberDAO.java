package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.MemberDTO;

public interface MemberDAO {

	//회원가입
	void joinMember(MemberDTO memberDTO);
	
	//내정보 조회
	MemberDTO findMemberById(String id);
	
	//내정보 수정
	MemberDTO modifyMember(String id, MemberDTO memberDTO);
	
	//회원탈퇴
	void deleteMember(String id);
	
	//회원전체목록(관리자)
	List<MemberDTO> list();
	
	//로그인
	MemberDTO loginMember(String id, String pw);
	
	//아이디찾기
	String findId(String name, String email);
	
	//비밀번호 찾기
	String findPw(String id, String name, String email);
}
