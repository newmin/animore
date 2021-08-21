package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proj.animore.dto.RboardDTO;
import com.proj.animore.dto.RboardListReqDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RboardDAOImpl implements RboardDAO{

	private final JdbcTemplate jt;
	
	/**
	 * 댓글등록처리
	 */
	@Transactional
	@Override
	public List<RboardListReqDTO> register(int bnum, String id, RboardDTO rboardDTO) {
//		임시로 세팅한거
		rboardDTO.setRgroup("1");
		rboardDTO.setRstep("1");
		
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
		List<RboardListReqDTO> list = all(bnum);
//		입력했던 댓글정보 리턴
		return list;
	}

	/**
	 * 댓글조회 by 댓글번호
	 * 댓글수정 클릭시 해당댓글정보 전달하려면 필요
	 */
	@Override
	public RboardListReqDTO findbyRnum(int rnum) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select rnum,bnum,id,rcontent,rgroup,rstep from rboard ");
		sql.append("where rnum=?");
		
		RboardListReqDTO rboardListReqDTO =
				jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(RboardListReqDTO.class), rnum);
		
		return rboardListReqDTO;
	}

	/**
	 * 댓글수정처리
	 */
	@Override
	public List<RboardListReqDTO> modify(int bnum, int rnum, String id, RboardDTO rboardDTO) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("update rboard ");
		sql.append("set rcontent=? ");
		sql.append("where bnum=? ");
		sql.append("and rnum=? ");
		sql.append("and id=? ");
		
		jt.update(sql.toString(),
										rboardDTO.getRcontent(), bnum, rnum, id);
		
		//수정후 댓글목록 갱신
		List<RboardListReqDTO> list = all(bnum);
		return list;
	}

	/**
	 * 댓글삭제처리
	 */
	@Override
	public List<RboardListReqDTO> del(int bnum, int rnum, String id) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from rboard ");
		sql.append("where rnum=? ");
		sql.append("and id=?");
		
		int result = 
				jt.update(sql.toString(), rnum, id);
		
		List<RboardListReqDTO> list = all(bnum);
		return list;
	}

	/**
	 * 댓글목록
	 */
	@Override
	public List<RboardListReqDTO> all(int bnum) {
		log.info(String.valueOf(bnum));
		StringBuffer sql = new StringBuffer();
		sql.append(" select t2.rnum,t1.nickname,t1.id,t2.rcontent,t2.rgroup,t2.rstep,t2.rcdate,t2.rgood ");
		sql.append(" from member t1, rboard t2 ");
		sql.append(" where t1.id=t2.id ");
		sql.append(" and bnum=? ");
		
		List<RboardListReqDTO> list =
				jt.query(sql.toString(), new BeanPropertyRowMapper<>(RboardListReqDTO.class), bnum);
		log.info(list.toString());
		return list;
	}

}
