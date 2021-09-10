package com.proj.animore.dao.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.board.RboardDTO;
import com.proj.animore.dto.board.RboardListReqDTO;

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
	@Override
	public List<RboardListReqDTO> register(int bnum, String id, RboardDTO rboardDTO) {

		StringBuffer sql = new StringBuffer();
		sql.append("insert into rboard(rnum,bnum,id,rcontent,rgroup,rstep,rindent) ");
		sql.append("						values(rboard_rnum_seq.nextval,?,?,?,rboard_rnum_seq.currval,0,0)");
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						sql.toString(),
						new String[] {"rnum"});
				
				pstmt.setInt(1, bnum);
				pstmt.setString(2, id);
				pstmt.setString(3, rboardDTO.getRcontent());
				
				return pstmt;
			}
		}, keyHolder);
		
		List<RboardListReqDTO> list = all(bnum);
		return list;
	}

	/**
	 * 대댓글등록처리
	 */
	@Override
	public List<RboardListReqDTO> addReReply(RboardDTO rboardDTO) {
		
		//부모글의 rgrouop중 rstep이 부모글의 rstep보다 큰 게시글 rstep + 1
		updateStep(rboardDTO.getRgroup(), rboardDTO.getRstep());
		
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO rboard ( ");
		sql.append("   rnum, ");
		sql.append("   bnum, ");
		sql.append("   id, ");
		sql.append("   rcontent, ");
		sql.append("   prnum, ");
		sql.append("   rgroup, ");
		sql.append("   rstep, ");
		sql.append("   rindent ");
		sql.append(" ) VALUES ( ");
		sql.append("   rboard_rnum_seq.nextval, ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   ?, ");
		sql.append("   ? ");
		sql.append(" ) ");
		
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						sql.toString(),
						new String[] {"rnum"});
				
				pstmt.setInt(1, rboardDTO.getBnum());
				pstmt.setString(2, rboardDTO.getId());
				pstmt.setString(3, rboardDTO.getRcontent());
				pstmt.setInt(4, rboardDTO.getPrnum());
				pstmt.setInt(5, rboardDTO.getRgroup());
				pstmt.setInt(6, rboardDTO.getRstep()+1);
				pstmt.setInt(7, rboardDTO.getRindent()+1);
				
				return pstmt;
			}
		}, keyHolder);
		List<RboardListReqDTO> list = all(rboardDTO.getBnum());
		return list;
	}
	
	private void updateStep(Integer rgroup, Integer rstep) {
		StringBuilder sql = new StringBuilder();
		sql.append("update rboard ");
		sql.append("   set rstep = rstep + 1 ");
		sql.append(" where rgroup = ? ");
//		sql.append("   and rstep < ? ");
		sql.append("   and rstep > ? ");	//원본
		
		jt.update(sql.toString(), rgroup,rstep);
	}
	
	
	/**
	 * 댓글조회 by 댓글번호
	 * 댓글수정 클릭시 해당댓글정보 전달
	 */
	@Override
	public RboardListReqDTO findbyRnum(int bnum, int rnum) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select t2.rnum,t1.nickname,t1.id,t2.rcontent,t2.rgroup,t2.rstep,t2.rindent,t2.rcdate,t2.rgood ");
		sql.append(" from member t1, rboard t2 ");
		sql.append(" where t1.id=t2.id ");
		sql.append(" and rnum=? ");
		
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
		sql.append("set rcontent=?, ");
		sql.append("    rudate=systimestamp ");
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
		sql.append("where bnum=? ");
		sql.append("and rnum=? ");
		sql.append("and id=?");
		
		int result = 
				jt.update(sql.toString(), bnum, rnum, id);
		
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
		sql.append("select t2.rnum,t1.nickname,t1.id,t2.rcontent,t2.prnum,t2.rgroup,t2.rstep,t2.rindent,t2.rcdate,t2.rgood ");
		sql.append("from member t1, rboard t2 ");
		sql.append("where t1.id=t2.id ");
		sql.append("and t2.bnum=? ");
//		sql.append("order by t2.rgroup desc, t2.rstep asc");	//원본
//		sql.append("order by t2.rgroup desc, t2.rstep desc, t2.rnum asc");
		sql.append("order by t2.rgroup asc, t2.rstep asc, rnum asc");
//		sql.append("order by t2.rgroup asc, t2.rstep desc, t2.rnum asc");
		
		List<RboardListReqDTO> list =
				jt.query(sql.toString(), new BeanPropertyRowMapper<>(RboardListReqDTO.class), bnum);
		log.info(list.toString());
		return list;
	}

}
