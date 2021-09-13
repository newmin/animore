package com.proj.animore.dao.business;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
		sql.append("  reviewfile_fnum_seq.nextval, ");
		sql.append("  ?, ");
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

	@Override
	public List<ReviewReq> findReviewFile(int rnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewReq> updateReviewFile(ReviewDTO reviewDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewReq> removeReviewFile(int rnum) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
