package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.MemberDTO;
import com.proj.animore.form.ChangePwForm;
import com.proj.animore.form.FindIdForm;
import com.proj.animore.form.FindPwForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberDAOImpl implements MemberDAO {

	private final JdbcTemplate jdbcTemplate;
	
	//회원가입
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
	
	//아이디 중복확인
	@Override
	public boolean isExistId(String id) {
			boolean isExistId = false;
			String sql = "select count(id) from member where id = ? ";
			int cnt = jdbcTemplate.update(sql, id);
			if(cnt >= 1) isExistId = true;
			return isExistId;
	}
	
	//닉네임 중복확인
	@Override
	public boolean isExistNickname(String nickname) {
		boolean isExistNickname = false;
		String sql = "select count(id) from member where nickname = ? ";
		int cnt = jdbcTemplate.update(sql, nickname);
		if(cnt >= 1) isExistNickname = true;
		return isExistNickname;
	}
	
	//내정보 조회
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

	//내정보수정
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

	//회원탈퇴
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

	//아이디 찾기
	@Override 
	public List<String> findId(FindIdForm findIdForm) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id ");
		sql.append("from member ");
		sql.append("where name = ? ");
		sql.append("  and email= ? ");
		
		log.info(findIdForm.toString());
		
		List<String> id =  jdbcTemplate.queryForList(sql.toString(),
												String.class,
												findIdForm.getName(),findIdForm.getEmail());	
		
		log.info("form:{}",id.toString());
//		log.info("id={}",id.getId());
		return id;
	}

	//비밀번호 찾기
	@Override
	public ChangePwForm findPw(FindPwForm findPwForm) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id, pw ");
		sql.append("from member ");
		sql.append("where id = ? ");
		sql.append("  and name= ? ");
		sql.append("  and email= ? ");

		ChangePwForm changePwForm = jdbcTemplate.queryForObject(sql.toString(),
				new BeanPropertyRowMapper<>(ChangePwForm.class),
				findPwForm.getId(), findPwForm.getName(), findPwForm.getEmail());
				
		return changePwForm;
	}

	//로그인확인
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

	//비밀번호 변경
	@Override
	public int changePW(ChangePwForm changePWForm) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("update member ");
		sql.append("   set pw=? ");
		sql.append(" where id=? ");
		
		int result = jdbcTemplate.update(sql.toString(), changePWForm.getPw(), changePWForm.getId());
		
		return result;
	}
}