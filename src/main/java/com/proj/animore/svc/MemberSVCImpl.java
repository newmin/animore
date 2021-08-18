package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.BusinessDAO;
import com.proj.animore.dao.MemberDAO;
import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.MemberDTO;
import com.proj.animore.dto.ProfessionDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {
	
	private final MemberDAO memberDAO;
	private final BusinessDAO businessDAO;

	@Override
	public void joinMember(MemberDTO memberDTO) {
		memberDAO.joinMember(memberDTO);
	}
	@Override
	public void joinMember(MemberDTO memberDTO, BusinessDTO businessDTO) {
		memberDAO.joinMember(memberDTO);
		businessDAO.joinBusi(businessDTO);
	
	}
	//TODO 전문가 회원가입
	@Override
	public void joinMember(MemberDTO memberDTO, ProfessionDTO professionDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemberDTO findMemberById(String id) {
		return memberDAO.findMemberById(id);
	}

	@Override
	public MemberDTO modifyMember(String id, MemberDTO memberDTO) {
		memberDAO.modifyMember(id, memberDTO);
		return memberDAO.findMemberById(id);
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
		
		return memberDAO.findByIdPw(id, pw);
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
