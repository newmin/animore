package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.ReviewReq;
import com.proj.animore.form.ReviewForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ReviewDAOImpl implements ReviewDAO {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<ReviewReq> registReview(Integer bnum, String id, ReviewForm reviewForm) {

		StringBuffer sql = new StringBuffer();
		sql.append("insert into review(rnum,bnum,rcontent,rscore,id,rvcdate) values(?,?,?,?,?,?) ");

		jdbcTemplate.update(sql.toString(),
							"review_rnum_seq.nextval", 
							bnum, 
							reviewForm.getRcontent(),
							reviewForm.getRscore(), 
							id, 
							"timestamp");

		// TODO 리뷰 작성시, 방문업체 확인 위한 영수증 등록절차?

		log.info("방문업체 : {}", bnum);
		log.info("방문일자 : {}");
		log.info("작성완료");

		// 예외처리
		// 접속이 만료되어, ID값이 존재하지 않는 경우
		// 리뷰 작성 중, 업체가 삭제가 되어 리뷰작성이 불가한 경우 / 업체정보(bnum)을 찾을 수 없는 경우
		// 등록된 영수증에 적힌 방문업체와 작성중인 업체가 다른 경우(실제로 다르든 api의 텍스트인식오류로 다르게 잡히든)
		// 등록된 영수증에 적힌 방문일자가 리뷰작성일과 차이가 많이 날 경우
		// 동일한 영수증으로 이미 리뷰를 작성한 경우
		// 평점or내용이 비어있는 경우

		return allReview(bnum);
	}

	@Override
	public List<ReviewReq> allReview(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		// TODO id,별칭 둘다 유니크 속성인데 review에는 id말고 별칭을 저장하는 안건.
		// 리뷰목록이 뜰때는 아이디가 아닌 별칭이 표시되는 만큼, 별칭을 저장하는 것이 불러오기 좋다고 생각.
		// TODO 리뷰는 수정일시를 저장하기보다, boolean으로 받아서 true라면 '수정됨'을 표시하는 안건
		sql.append("select rv.id,rv.rcontent,rscore,rvcdate,rnum ");
		sql.append("  from review rv, member m ");
		sql.append(" where bnum = ? ");
		sql.append("   and rv.id=m.id ");

		List<ReviewReq> list = jdbcTemplate.query(sql.toString(), 
										   new BeanPropertyRowMapper<>(ReviewReq.class),
										   bnum);

//		log.info("접속시간 : {}");
//		해당 업체에 얼마나 자주 접속하는지에 대한 로그 남기고 싶음
		
		//예외처리
		//비로그인 상태이거나 접속이 만료되어, ID값이 존재하지 않는 경우
		//전체조회값이 null인 경우 / 이건 다른 화면 표시하되, 예외는 아닌가?
		

		return list;
	}

	@Override
	public List<ReviewReq> myReview(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,recontent,rscore,rvcdate ");
		sql.append("  from review ");
		sql.append(" where id = ? ");

		List<ReviewReq> list = jdbcTemplate.query(sql.toString(), 
								new BeanPropertyRowMapper<>(ReviewReq.class), 
								id);

		//예외처리
		//접속만료되어 id값 없는 경우
		//작성한 리뷰가 없는 경우? 예외?
		
		return list;
	}

	@Override
	public List<ReviewReq> updateReview(Integer bnum, String id, ReviewForm reviewForm) {
		StringBuffer sql = new StringBuffer();
		sql.append("update review ");
		sql.append("   set rcontent = ?, ");
		sql.append("   	   rscore =?, ");
		sql.append("   	   rvudate =? ");
		sql.append(" where bnum = ? ");
		sql.append("   and id =? ");

		jdbcTemplate.update(sql.toString(), 
							reviewForm.getRcontent(), 
							reviewForm.getRscore(), 
							"timestamp", 
							bnum, id);
		
		//예외처리
		//접속만료되어 id값 없는 경우
		//수정 중 업체가 삭제된 경우? 업체정보(bnum)를 찾을 수 없는 경우
		//수정된 폼에서 내용or평점의 값이 비어있는 경우
		

		return allReview(bnum);
	}

	@Override
	public List<ReviewReq> removeReview(Integer bnum, String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from review ");
		sql.append(" where bnum = ? ");
		sql.append("   and id = ? ");

		jdbcTemplate.update(sql.toString(), bnum, id);

		//예외처리
		//접속만료 등 id값을 찾을 수 없는 경우
		//업체삭제 등 bnum을 찾을 수 없는 경우
		
		return allReview(bnum);
	}

}
