package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.MemberDTO;
import com.proj.animore.form.ChangePwForm;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.FindPwForm;

public interface MemberDAO {

	//회원가입
	void joinMember(MemberDTO memberDTO);
	
	//아이디 중복확인
	boolean isExistId(String id);
	
	//닉네임 중복확인
	boolean isExistNickname(String nickname);
	
	//내정보 조회
	MemberDTO findMemberById(String id);
	
	//내정보 수정
	MemberDTO modifyMember(String id, MemberDTO memberDTO);
	
	//회원탈퇴
	void deleteMember(String id);
	
	//회원전체목록(관리자)
	List<MemberDTO> list();
	
	//로그인
	MemberDTO findByIdPw(String id, String pw);
	
	//아이디찾기
	List<String> findId(FindIdForm findIdForm);
	
	//비밀번호 찾기
	ChangePwForm findPw(FindPwForm findPwForm);

	//비밀번호변경처리
	int changePW(ChangePwForm changePWForm);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 로그인 체크
	 * @param id
	 * @param pw
	 * @return
	 */
	boolean isLogin(String id, String pw);
	
	/**
	 * 탈퇴
	 * @param email
	 */
	void outMember(String id, String pw);
	
	void changePw(String id, String pw, String tmpPw);
}