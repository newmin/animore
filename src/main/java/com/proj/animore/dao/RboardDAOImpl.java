package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proj.animore.dto.RboardDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RboardDAOImpl implements RboardDAO{

	private final JdbcTemplate jt;
	
	/**
	 * 댓글등록처리
	 */
	@Transactional
	@Override
	public List<RboardDTO> register(int bnum, String id, RboardDTO rboardDTO) {
		
//		시퀀스번호 따기
		String sql = "select rboard_rnum_seq.nextval from dual";
		String seq = jt.queryForObject(sql, String.class);
		
//		입력
		StringBuffer sql2 = new StringBuffer();
		sql2.append("insert into rboard(rnum,bnum,id,rcontent,rgroup,rstep) ");
		sql2.append("						values(?,?,?,?,?,?)");
		
		jt.update(sql2.toString()
							,seq
							,bnum
							,id
							,rboardDTO.getRcontent()
							,rboardDTO.getRgroup()
							,rboardDTO.getRstep()
						 );
		
////		따놓은 시퀀스번호로 입력한 댓글정보 확인, 받기
//		RboardDTO retDTO = findbyRnum(seq);
		
		//댓글단 게시글의 댓글목록 갱신.=> 댓글목록 리턴
		List<RboardDTO> list = all(bnum);
//		입력했던 댓글정보 리턴
		return list;
	}

	/**
	 * 댓글조회 by 댓글번호
	 * 댓글수정 클릭시 해당댓글정보 전달하려면 필요
	 */
	@Override
	public RboardDTO findbyRnum(int rnum) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select rnum,bnum,id,rcontent,rgroup,rstep from rboard ");
		sql.append("where rnum=?");
		
		RboardDTO rboardDTO =
				jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(RboardDTO.class), rnum);
		
		return rboardDTO;
	}

	/**
	 * 댓글수정처리
	 */
	@Override
	public List<RboardDTO> modify(int bnum, int rnum, String id, RboardDTO rboardDTO) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("update rboard ");
		sql.append("set rcontent=? ");
		sql.append("where bnum=? ");
		sql.append("and rnum=? ");
		sql.append("and id=? ");
		
		jt.update(sql.toString(),
										rboardDTO.getRcontent(), bnum, rnum, id);
		
		//수정후 댓글목록 갱신
		List<RboardDTO> list = all(bnum);
		return list;
	}

	/**
	 * 댓글삭제처리
	 */
	@Override
	public List<RboardDTO> del(int bnum, int rnum, String id) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from rboard ");
		sql.append("where rnum=? ");
		sql.append("and id=?");
		
		int result = 
				jt.update(sql.toString(), rnum, id);
		
		List<RboardDTO> list = all(bnum);
		return list;
	}

	@Override
	public List<RboardDTO> all(int bnum) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select rnum,id,rcontent,rgroup,rstep from rboard ");
		sql.append("where bnum=?");
		
		List<RboardDTO> list =
				jt.query(sql.toString(), new BeanPropertyRowMapper<>(RboardDTO.class), bnum);
		
		return list;
	}

}
