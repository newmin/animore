package com.proj.animore.dao.business;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.business.BusiUploadFileDTO;
import com.proj.animore.dto.business.ReviewDTO;
import com.proj.animore.dto.business.ReviewReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ReviewFileDAOImpl implements ReviewFileDAO {

	private final JdbcTemplate jdbcTemplate;
	
	//리뷰 파일 등록
	@Override
	public void registReviewFile(int rnum, List<BusiUploadFileDTO> files) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into reviewfile ( ");
		sql.append("  fnum, ");
		sql.append("  rnum, ");
		sql.append("  store_fname,  ");
		sql.append("  upload_fname, ");
		sql.append("  fsize,  ");
		sql.append("  ftype ");
		sql.append(") ");
		sql.append("values( ");
		sql.append("  reviewfile_rnum_seq.nextval, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ? ");
		sql.append(") ");		

		jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, rnum);
				ps.setString(2, files.get(i).getStore_fname());
				ps.setString(3, files.get(i).getUpload_fname());
				ps.setString(4, files.get(i).getFsize());
				ps.setString(5, files.get(i).getFtype());
			}

			@Override
			public int getBatchSize() {
				return files.size();
			}
		});//batchUpdate
	}
	//리뷰 첨부파일 조회
	@Override
	public List<BusiUploadFileDTO> findReviewFile(int rnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select fnum,rnum,store_fname,upload_fname, ");
		sql.append("       fsize,ftype,cdate,udate ");
		sql.append("  from reviewfile  ");
		sql.append(" where rnum = ? ");
		
		List<BusiUploadFileDTO> list = 
				jdbcTemplate.query( sql.toString(), 
									new BeanPropertyRowMapper<>(BusiUploadFileDTO.class), 
									rnum );
		
		return list;
	}

	@Override
	public List<ReviewReq> updateReviewFile(ReviewDTO reviewDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	//리뷰 첨부파일 전체 삭제(리뷰삭제)
	@Override
	public void removeReviewFile(int rnum) {
		String sql = "delete from reviewfile where rnum = ? ";
		jdbcTemplate.update(sql, rnum);
	}
	
	//첨부파일명 불러오기
	@Override
	public List<String> getStore_Fname(int rnum) {
		
		String sql = "select store_fname from reviewfile where rnum = ? ";
		
		List<String> store_fnames = 
				jdbcTemplate.queryForList(sql, String.class, rnum);
		
		return store_fnames;
	}
	
	//첨부파일 개별 삭제
	@Override
	public void removeEachFile(int fnum) {
		
		String sql = "delete from reviewfile where fnum = ? ";
		
		jdbcTemplate.update(sql, fnum);
		
	}
}
