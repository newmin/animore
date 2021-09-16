package com.proj.animore.dao.business;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ReviewDAOImpl implements ReviewDAO {

	private final JdbcTemplate jdbcTemplate;

	//리뷰등록
	@Override
	public int registReview(ReviewDTO reviewDTO) {

		StringBuffer sql = new StringBuffer();
		sql.append("insert into review(rnum,bnum,rcontent,rscore,id) values(review_rnum_seq.nextval,?,?,?,?) ");

		jdbcTemplate.update(sql.toString(),
							reviewDTO.getBnum(), 
							reviewDTO.getRcontent(),
							reviewDTO.getRscore(), 
							reviewDTO.getId());

		// TODO 리뷰 작성시, 방문업체 확인 위한 영수증 등록절차?


		// 예외처리(컨트롤러)
		// (완)접속이 만료되어, ID값이 존재하지 않는 경우
		// 리뷰 작성 중, 업체가 삭제가 되어 리뷰작성이 불가한 경우 / 업체정보(bnum)을 찾을 수 없는 경우
		// 등록된 영수증에 적힌 방문업체와 작성중인 업체가 다른 경우(실제로 다르든 api의 텍스트인식오류로 다르게 잡히든)
		// 등록된 영수증에 적힌 방문일자가 리뷰작성일과 차이가 많이 날 경우
		// 동일한 영수증으로 이미 리뷰를 작성한 경우
		// (완)평점or내용이 비어있는 경우

		return rnumCurrVal();
	}

	//업체별 리뷰조회(최신순)
	@Override
	public List<ReviewReq> allReview(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select rv.id,rv.rnum,rv.rcontent,rscore,rvcdate,rnum,m.nickname,rvudate, rvReply,m.store_fname ");
		sql.append("  from review rv, member m ");
		sql.append(" where bnum = ? ");
		sql.append("   and rv.id=m.id ");
		sql.append(" order by rvcdate desc ");

		List<ReviewReq> list = jdbcTemplate.query(sql.toString(), 
										   new BeanPropertyRowMapper<>(ReviewReq.class),
										   bnum);

//		log.info("접속시간 : {}");
//		해당 업체에 얼마나 자주 접속하는지에 대한 로그 남기고 싶음
		
		//예외처리
		//(완)비로그인 상태이거나 접속이 만료되어, ID값이 존재하지 않는 경우
		//(완)전체조회값이 null인 경우 / 이건 다른 화면 표시하되, 예외는 아닌가?
		

		return list;
	}
	//내리뷰조회
	@Override
	public List<ReviewReq> myReview(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select rv.id,rnum,rcontent,rscore,rvcdate,rvudate,rv.bnum, b.bname ");
		sql.append("  from review rv, business b ");
		sql.append(" where rv.bnum = b.bnum ");
		sql.append("   and rv.id = ? ");

		List<ReviewReq> list = jdbcTemplate.query(sql.toString(), 
								new BeanPropertyRowMapper<>(ReviewReq.class), 
								id);

		//예외처리
		//접속만료되어 id값 없는 경우
		//작성한 리뷰가 없는 경우? 예외?
		
		return list;
	}
	
	//리뷰 1개짜리(리뷰수정시 호출)
	@Override
	public ReviewReq findReview(int rnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select rscore, rcontent,rvcdate,rnum, m.nickname, rvReply ");
		sql.append("  from review r, member m ");
		sql.append(" where rnum = ?	 ");
		sql.append("   and r.id = m.id ");
		
		ReviewReq reviewReq = 	jdbcTemplate.queryForObject(sql.toString(),new BeanPropertyRowMapper<>(ReviewReq.class),rnum);
		return reviewReq;
	}
	
	//리뷰수정
	@Override
	public void updateReview(ReviewDTO reviewDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("update review ");
		sql.append("   set rcontent = ?, ");
		sql.append("   	   rscore =?, ");
		sql.append("   	   rvudate = systimestamp ");
		sql.append(" where rnum = ? ");

		jdbcTemplate.update(sql.toString(), 
							reviewDTO.getRcontent(), 
							reviewDTO.getRscore(), 
							reviewDTO.getRnum());
		
		//예외처리
		//접속만료되어 id값 없는 경우
		//수정 중 업체가 삭제된 경우? 업체정보(bnum)를 찾을 수 없는 경우
		//수정된 폼에서 내용or평점의 값이 비어있는 경우
		
	}
	//리뷰삭제
	@Override
	public void removeReview(int rnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from review ");
		sql.append(" where rnum = ? ");

		jdbcTemplate.update(sql.toString(), rnum);

		//예외처리
		//접속만료 등 id값을 찾을 수 없는 경우
		//업체삭제 등 bnum을 찾을 수 없는 경우
		
	}
	
  //사장님 이전 리뷰리댓 조회
	@Override
	public ReviewReq findRvReply(int rnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select rvReply ");
		sql.append("  from review ");
		sql.append(" where rnum = ?	");
		ReviewReq reviewReq = jdbcTemplate.queryForObject(sql.toString(),new BeanPropertyRowMapper<>(ReviewReq.class),rnum);
		return reviewReq;
	}
	
  //사장님 리뷰리댓등록
	@Override
	public List<ReviewReq> addRvReply(ReviewReq reviewReq) {
		StringBuffer sql = new StringBuffer();
		sql.append("update review ");
		sql.append("   set rvReply =? ");
		sql.append(" where rnum =? ");
		jdbcTemplate.update(sql.toString(),reviewReq.getRvReply(),reviewReq.getRnum());
		
		return allReview(reviewReq.getBnum());
	}
	//사장님 리뷰리댓 삭제
	@Override
	public void delRvReply(int rnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update review ");
		sql.append("   set rvReply = null ");
		sql.append(" where rnum =? ");
		jdbcTemplate.update(sql.toString(),rnum);
		
	}


	@Override
	public Integer rnumCurrVal() {
		String sql = "select REVIEW_RNUM_SEQ.currval from dual ";
		int rnum = jdbcTemplate.queryForObject(sql,Integer.class);
		return rnum;
	}
}
