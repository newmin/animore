package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.BoardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO {
	
	private final JdbcTemplate jt;
	
	//게시글등록
	@Override
	public void addBoard(BoardDTO boardDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into board(bnum,bcategory,btitle,id,bcontent) ");
		sql.append("   values(board_bnum_seq.nextval,?,?,?,?) ");
		jt.update(sql.toString(),
					boardDTO.getBcategory(),
					boardDTO.getBtitle(),
					boardDTO.getId(),
					boardDTO.getBcontent());
		
		log.info("boardDTO:{}",boardDTO.toString());
		
	}
	//게시글조회
	@Override
	public BoardDTO findBoardByBnum(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select bcategory,btitle,id,bcdate,bhit,bgood,breply,bcontent,bnum ");
		sql.append("from board ");
		sql.append("where bnum=? ");
		
		BoardDTO boardDTO = jt.queryForObject(sql.toString(), 
											new BeanPropertyRowMapper<>(BoardDTO.class),
											bnum);
		return boardDTO;
	}
	//게시글검색(by id)
	@Override
	public List<BoardDTO> findBoardById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select bcategory,btitle,id,bcdate,bhit,bgood,breply,bcontent ");
		sql.append("from board ");
		sql.append("where id=? ");
		
		List<BoardDTO> boardDTO = jt.query(sql.toString(), 
												new BeanPropertyRowMapper<>(BoardDTO.class),
												id);
		return boardDTO;
		
	}
	//게시글검색(by btitle)
	@Override
	public List<BoardDTO> findBoardByBtitle(String btitle) {
		StringBuffer sql = new StringBuffer();
		sql.append("select bcategory,btitle,id,bcdate,bhit,bgood,breply,bcontent ");
		sql.append("from board ");
		sql.append("where btitle like '%?%'; ");
		
		List<BoardDTO> boardDTO = jt.query(sql.toString(), 
				new BeanPropertyRowMapper<>(BoardDTO.class),
				btitle);
		return boardDTO;

	}
	//게시글검색(by bcontent)
	@Override
	public List<BoardDTO> findBoardByBcontent(String bcontent) {

		StringBuffer sql = new StringBuffer();
		sql.append("select bcategory,btitle,id,bcdate,bhit,bgood,breply,bcontent ");
		sql.append("from board ");
		sql.append("where bcontent	 like '%?%'; ");
		
		List<BoardDTO> boardDTO = jt.query(sql.toString(), 
				new BeanPropertyRowMapper<>(BoardDTO.class),
				bcontent);
		return boardDTO;

	}
	//게시글수정
	@Override
	public BoardDTO modifyBoard(int bnum, BoardDTO boardDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("set bcategory =?, ");
		sql.append("    btitle=?, ");
		sql.append("    budate= current_timestamp, ");
		sql.append("    bcontent =? ");
		sql.append("where bnum=? ");
		
		jt.update(sql.toString(),
								boardDTO.getBcategory(),
								boardDTO.getBtitle(),
								boardDTO.getBcontent(),
								bnum);
		return findBoardByBnum(bnum);
	}
	//게시글삭제
	@Override
	public void deleteBoard(int bnum) {
		String sql ="delete board where bnum=?";
		jt.update(sql.toString(),bnum);
		
	}
	//게시글전체목록(by bcategory)
	@Override
	public List<BoardDTO> list(String bcategory) {
		String sql ="select bnum,bhit,bgood,btitle,id,bcdate,bcategory,breply,bcontent from board where bcategory=?";
		List<BoardDTO> boardDTO = jt.query(sql.toString(),
										new BeanPropertyRowMapper<>(BoardDTO.class),
										bcategory);
		return boardDTO;
	}

}
