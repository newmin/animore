package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dto.ChangPwReq;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.ProfessionDTO;
import com.proj.animore.dto.business.BcategoryDTO;
import com.proj.animore.dto.business.BusinessDTO;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.FindPwForm;
import com.proj.animore.form.ChangePwForm;

public interface MemberSVC {

	//회원가입
	void joinMember(MemberDTO memberDTO);
	void joinMember(MemberDTO memberDTO, BusinessDTO businessDTO, BcategoryDTO bcategoryDTO);
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
	MemberDTO changePW(String id,ChangPwReq changPwReq);
	
	/**
	 * 회원 유무체크
	 * @param email
	 * @param pw
	 */
	boolean isMemember(String id, String pw);
	
	/**
	 * 탈퇴
	 * @param id
	 */
	void outMember(String id);
	
	/**
	 * 비밀번호변경(이메일로 임시비밀번호 발급 기능 사용시, 생성된 임시비밀번호를 비밀번호찾기 신청한 회원정보에 업데이트하는 메소드)
	 * @param email
	 * @param pw
	 * @param tmpPw
	 */
	void changePw(String id, String pw, String tmpPw);
	
}
