package com.proj.animore.svc.board;

import java.util.List;

import com.proj.animore.dto.board.RboardDTO;
import com.proj.animore.dto.board.RboardListReqDTO;

public interface RboardSVC {

	/**
	 * 댓글등록처리
	 * @param bnum
	 * @param id
	 * @param rboardDTO
	 * @return
	 */
	List<RboardListReqDTO> register(int bnum, String id, RboardDTO rboardDTO);

	/**
	 * 댓글수정처리
	 * @param rnum
	 * @param id
	 * @param rboardDTO
	 * @return
	 */
	List<RboardListReqDTO> modify(int bnum, int rnum, String id, RboardDTO rboardDTO);

	/**
	 * 댓글조회 by 댓글번호
	 * @param rnum2 
	 */
	RboardListReqDTO findByRnum(int bnum, int rnum);

	/**
	 * 댓글삭제 by 댓글번호, 사용자id
	 */
	List<RboardListReqDTO> del(int bnum, int rnum, String id);

	/**
	 * 댓글목록조회 by 게시글번호
	 */
	List<RboardListReqDTO> all(int bnum);

}
