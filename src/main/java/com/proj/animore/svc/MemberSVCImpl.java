package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.MemberDAO;
import com.proj.animore.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {
	
	private final MemberDAO memberDAO;

	@Override
	public void joinMember(MemberDTO memberDTO) {
		memberDAO.joinMember(memberDTO);
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
