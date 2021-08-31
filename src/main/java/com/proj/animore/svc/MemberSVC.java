package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.ProfessionDTO;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.FindPwForm;
import com.proj.animore.form.ChangePwForm;

public interface MemberSVC {

	//회원가입
	void joinMember(MemberDTO memberDTO);
	void joinMember(MemberDTO memberDTO, BusinessDTO businessDTO);
	//TODO 전문가가입
	void joinMember(MemberDTO memberDTO, ProfessionDTO professionDTO);
	
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

	//비밀번호 변경처리
	int changePW(ChangePwForm changePWForm);
	
	
	
	
	
	/**
	 * 회원 유무체크
	 * @param email
	 * @param pw
	 */
	boolean isMemember(String id, String pw);
	
	/**
	 * 탈퇴
	 * @param email
	 */
	void outMember(String email, String pw);
	
}
