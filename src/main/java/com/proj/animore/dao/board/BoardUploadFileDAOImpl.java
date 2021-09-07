package com.proj.animore.dao.board;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.board.BoardUploadFileDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardUploadFileDAOImpl implements BoardUploadFileDAO {
	
	private final JdbcTemplate jt;
	
	
	@Override
	public void addFile(BoardUploadFileDTO boardUploadFileDTO) {
		List<BoardUploadFileDTO> list = new ArrayList<>();
		list.add(boardUploadFileDTO);
		addFiles(list);
		
	}
	@Override
	public void addFiles(List<BoardUploadFileDTO> list) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO boardfile ( ");
		sql.append("    fnum, ");
		sql.append("    bnum, ");
		sql.append("    store_fname, ");
		sql.append("    upload_fname, ");
		sql.append("    fsize, ");
		sql.append("    ftype ");
		sql.append("  ) VALUES ( ");
		sql.append("    boardfile_fnum_seq.nextval, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ? ");
		sql.append("  ) ");
		
		jt.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, list.get(i).getBnum());
				ps.setString(2, list.get(i).getStore_fname());
				ps.setString(3, list.get(i).getUpload_fname());
				ps.setString(4, list.get(i).getFsize());
				ps.setString(5, list.get(i).getFtype());
				
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	@Override
	public List<BoardUploadFileDTO> getFiles(Integer bnum) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select fnum,bnum,store_fname,upload_fname,fsize,ftype,cdate,udate ");
		sql.append("from boardfile ");
		sql.append("where bnum=? ");

		List<BoardUploadFileDTO> list = 
					jt.query(sql.toString(), 
									new BeanPropertyRowMapper<>(BoardUploadFileDTO.class),
									bnum);
		
		return list;
	}
	
	//첨부파일 삭제 by bnum
	@Override
	public void deleteFileByBnum(Integer bnum) {
		String sql = "delete from boardfile where bnum=? ";
		jt.update(sql,bnum);
		
	}
	
	//서버보관 파일명 가져오기 by bnum
	@Override
	public List<String> getStore_Fname(Integer bnum) {
		
		String sql = "select store_fname from boardfile where bnum =? ";
		List<String> store_fname = jt.queryForList(sql, String.class, bnum);
		
		return store_fname;
	}
}
