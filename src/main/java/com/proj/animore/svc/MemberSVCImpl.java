package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proj.animore.common.file.FileStore;
import com.proj.animore.dao.MemberDAO;
import com.proj.animore.dao.business.BcategoryDAO;
import com.proj.animore.dao.business.BusinessDAO;
import com.proj.animore.dao.business.BusinessFileDAO;
import com.proj.animore.dto.ChangPwReq;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.ProfessionDTO;
import com.proj.animore.dto.business.BcategoryDTO;
import com.proj.animore.dto.business.BusiUploadFileDTO;
import com.proj.animore.dto.business.BusinessDTO;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.FindPwForm;
import com.proj.animore.form.ChangePwForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {
	
	private final MemberDAO memberDAO;
	private final BusinessDAO businessDAO;
	private final BusinessFileDAO businessFileDAO;
	private final BcategoryDAO bcategoryDAO;
	private final FileStore fileStore;
	
	//일반회원가입
	@Override
	public void joinMember(MemberDTO memberDTO) {
		memberDAO.joinMember(memberDTO);
	}
	
	//사업가회원 가입
	@Override
	@Transactional
	public void joinMember(MemberDTO memberDTO, BusinessDTO businessDTO, BcategoryDTO bcategoryDTO) {
		memberDAO.joinMember(memberDTO);
		int bnum = businessDAO.joinBusi(businessDTO);
		if(businessDTO.getFiles() !=null && businessDTO.getFiles().size() > 0) {		
			businessFileDAO.addBusiFile(convert(bnum, businessDTO.getFiles()));
		}
		bcategoryDAO.addBcategory(bcategoryDTO);
	}
	
	private List<BusiUploadFileDTO> convert(Integer bnum,List<BusiUploadFileDTO> files){
		for(BusiUploadFileDTO bdto : files) {bdto.setRefer_num(bnum);}
		return files;
	}
	
	
	//TODO 전문가 회원가입
	@Override
	public void joinMember(MemberDTO memberDTO, ProfessionDTO professionDTO) {
		// TODO Auto-generated method stub
		
	}
	
	//아이디 중복확인
	@Override
	public boolean isExistId(String id) {
		return memberDAO.isExistId(id);
	}
	
	//닉네임 중복확인
	@Override
	public boolean isExistNickname(String nickname) {
		return memberDAO.isExistNickname(nickname);
	}
	
	//회원찾기 id
	@Override
	public MemberDTO findMemberById(String id) {
		return memberDAO.findMemberById(id);
	}

	//회원정보 수정
	@Override
	public MemberDTO modifyMember(String id, MemberDTO memberDTO) {
		memberDAO.modifyMember(id, memberDTO);
		return memberDAO.findMemberById(id);
	}

	//회원탈퇴
	@Override
	public void deleteMember(String id) {
		memberDAO.deleteMember(id);
	}

	//회원목록 전체조회
	@Override
	public List<MemberDTO> list() {
		// TODO Auto-generated method stub
		return null;
	}

	//로그인시 멤버확인
	@Override
	public MemberDTO findByIdPw(String id, String pw) {
		return memberDAO.findByIdPw(id, pw);
	}

	//아이디 찾기
	@Override
	public List<String> findId(FindIdForm findIdForm) {
		return memberDAO.findId(findIdForm);
	}

	//비밀번호 찾기
	@Override
	public ChangePwForm findPw(FindPwForm findPwForm) {
		return memberDAO.findPw(findPwForm);
	}

	//비밀번호 변경
	@Override
	public MemberDTO changePW(String id,ChangPwReq changPwReq) {
		return memberDAO.changePW(id, changPwReq);
	}
	
	
	//회원 우무체크
	@Override
	public boolean isMemember(String id, String pw) {
		return memberDAO.isLogin(id, pw);
	}
	
//id로 회원 탈퇴
	@Override
	public void outMember(String id) {
		memberDAO.outMember(id);
		
	}

	
//비밀번호변경(이메일로 임시비밀번호 발급 기능 사용시, 생성된 임시비밀번호를 비밀번호찾기 신청한 회원정보에 업데이트하는 메소드)
	@Override
	public void changePw(String email, String pw, String tmpPw) {
		// TODO Auto-generated method stub
		memberDAO.changePw(email, pw, tmpPw);
	}
	
}
