package com.proj.animore.dao.business;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.business.BusiUploadFileDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BusinessFileDAOImpl implements BusinessFileDAO {

	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public void addBusiFile(List<BusiUploadFileDTO> files) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into businessfile ( ");
		sql.append("  fnum, ");
		sql.append("  bnum, ");
		sql.append("  store_fname,  ");
		sql.append("  upload_fname, ");
		sql.append("  fsize,  ");
		sql.append("  ftype ");
		sql.append(") ");
		sql.append("values( ");
		sql.append("  businessfile_fnum_seq.nextval, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ?, ");
		sql.append("  ? ");
		sql.append(") ");		

		jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, files.get(i).getRefer_num());
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
	public List<BusiUploadFileDTO> getBusiFiles(int bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select fnum,bnum,store_fname,upload_fname, ");
		sql.append("       fsize,ftype,cdate,udate ");
		sql.append("  from businessfile  ");
		sql.append(" where bnum = ? ");
		
		List<BusiUploadFileDTO> list = 
				jdbcTemplate.query( sql.toString(), 
									new BeanPropertyRowMapper<>(BusiUploadFileDTO.class), 
									bnum );
		
		return list;
	}

	@Override
	public void updateBusiFiles(List<BusiUploadFileDTO> files) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBusiFiles(int bnum) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getStore_Fname(int bnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEachFile(int fnum) {
		// TODO Auto-generated method stub

	}

}
