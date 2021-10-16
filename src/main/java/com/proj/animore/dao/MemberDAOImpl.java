package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.ChangPwReq;
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
		sql.append("insert into member(id,pw,name,birth,gender,tel,tel2,tel3,email,address,nickname,mtype,myani,upload_fname,store_fname,ftype,fsize) ");
		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		
		jdbcTemplate.update(sql.toString(),
							memberDTO.getId(),
							memberDTO.getPw(),
							memberDTO.getName(),
							memberDTO.getBirth(),
							memberDTO.getGender(),
							memberDTO.getTel(),
							memberDTO.getTel2(),
							memberDTO.getTel3(),
							memberDTO.getEmail(),
							memberDTO.getAddress(),
							memberDTO.getNickname(),
							memberDTO.getMtype(),
							memberDTO.getMyAni(),
							memberDTO.getUpload_fname(),
							memberDTO.getStore_fname(),
							memberDTO.getFtype(),
							memberDTO.getFsize());

			log.info("memberDTO : {}", memberDTO.toString());
	}
	
	//아이디 중복확인
	@Override
	public boolean isExistId(String id) {
			boolean isExistId = false;
			String sql = "select count(id) from member where id = ? ";
			int cnt = jdbcTemplate.queryForObject(sql, Integer.class, id);
			if(cnt >= 1) isExistId = true;
			return isExistId;
	}
	
	//닉네임 중복확인
	@Override
	public boolean isExistNickname(String nickname) {
		boolean isExistNickname = false;
		String sql = "select count(id) from member where nickname = ? ";
		int cnt = jdbcTemplate.queryForObject(sql, Integer.class, nickname);
		if(cnt >= 1) isExistNickname = true;
		return isExistNickname;
	}
	
	//내정보 조회
	@Override
	public MemberDTO findMemberById(String id) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select id,pw,name,birth,gender,tel,tel2,tel3,email,address,nickname,upload_fname,store_fname,ftype,fsize,mileage ");
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
		sql.append("   set name = ?, ");
		sql.append("       tel = ?, ");
		sql.append("       tel2 = ?, ");
		sql.append("       tel3 = ?, ");
		sql.append("       email = ?, ");
		sql.append("       address = ?, ");
		sql.append("       nickname = ?, ");
		sql.append("       udate = systimestamp ");
		sql.append(" where id = ? ");
		jdbcTemplate.update(sql.toString(),
							memberDTO.getName(),
							memberDTO.getTel(),
							memberDTO.getTel2(),
							memberDTO.getTel3(),
							memberDTO.getEmail(),
							memberDTO.getAddress(),
							memberDTO.getNickname(),
							id);
		return findMemberById(id);

	}

	//회원정보삭제
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
		sql.append("  and not status= 'W' ");	//탈퇴회원 제외하고 찾기
		
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
		sql.append("  and not status= 'W' ");

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
	      sql.append("  and not status = 'W' ");
	      
	     MemberDTO memberDTO = jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(MemberDTO.class),id,pw);
	      return memberDTO;

	}

	//비밀번호 변경
	@Override
	public MemberDTO changePW(String id,ChangPwReq changPwReq) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("update member ");
		sql.append("   set pw=?, udate=systimestamp ");
		sql.append(" where id=? ");
		sql.append("   and pw = ? ");
		
		jdbcTemplate.update(sql.toString(),changPwReq.getPwChk(),id, changPwReq.getPw() );
		return findMemberById(id);
	}
	

	//로그인확인
	@Override
	public boolean isLogin(String id, String pw) {
		boolean isLogin = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) ");
		sql.append("  from member ");
		sql.append(" where id = ? ");
		sql.append("   and pw = ? ");
		sql.append("   and status='A' ");
		
		Integer cnt = 
				jdbcTemplate.queryForObject(sql.toString(), Integer.class, id, pw);
		if(cnt == 1) isLogin = true;
		
		return isLogin;
	}
	//회원탈퇴
	@Override
	public void outMember(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("update member ");
		sql.append("   set status = 'W', ");		
		sql.append("   		 udate = systimestamp ");		
		sql.append(" where id = ? ");
		
		jdbcTemplate.update(sql.toString(), id);
		
	}
	
	//비밀번호찾기 - 발급된 임시비밀번호 업데이트
	@Override
	public void changePw(String email, String pw, String tmpPw) {
		StringBuffer sql = new StringBuffer();
		sql.append("update member ");
		sql.append("	 set pw = ?, udate=systimestamp ");   	//변경할 비밀번호
		sql.append(" where email = ? ");
		sql.append("   and pw = ? ");   	//이전 비밀번호
		sql.append("   and status='A' ");
		
		jdbcTemplate.update(sql.toString(), tmpPw, email, pw);
		
	}
	
	//마일리지증가
	@Override
	public int upMileage(String id, int mileage) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update member ");
		sql.append(" set mileage = mileage + ? ");
		sql.append(" where id= ? ");
		
		jdbcTemplate.update(sql.toString(), mileage, id);
		return 0;
	}
	//마일리지감소
	@Override
	public int downMileage(String id, int mileage) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update member ");
		sql.append(" set mileage = mileage - ? ");
		sql.append(" where id= ? ");
		
		jdbcTemplate.update(sql.toString(), mileage, id);
		return 0;
	}
}