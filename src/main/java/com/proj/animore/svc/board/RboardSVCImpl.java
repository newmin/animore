package com.proj.animore.svc.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proj.animore.dao.board.BoardDAO;
import com.proj.animore.dao.board.RboardDAO;
import com.proj.animore.dto.board.RboardDTO;
import com.proj.animore.dto.board.RboardListReqDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RboardSVCImpl implements RboardSVC {

	private final RboardDAO rboardDAO;
	private final BoardDAO boardDAO;
	
	/**
	 * 댓글등록처리
	 * @param bnum
	 * @param id
	 * @param rboardDTO
	 * @return
	 */
	@Override

	public List<RboardListReqDTO> register(int bnum, String id, RboardDTO rboardDTO) {
		boardDAO.upRcount(bnum);
		return rboardDAO.register(bnum, id, rboardDTO);
	}
	
	/**
	 * 대댓글등록처리
	 */
	@Override
	public List<RboardListReqDTO> addReReply(RboardDTO rboardDTO) {
		boardDAO.upRcount(rboardDTO.getBnum());
		return rboardDAO.addReReply(rboardDTO);
	}
	
	/**
	 * 댓글수정처리
	 * @param rnum
	 * @param id
	 * @param rboardDTO
	 * @return
	 */
	@Override
	public List<RboardListReqDTO> modify(int bnum, int rnum, String id, RboardDTO rboardDTO) {
		return rboardDAO.modify(bnum, rnum, id, rboardDTO);
	}
	
	/**
	 * 댓글조회 by 댓글번호
	 */
	@Override
	public RboardListReqDTO findByRnum(int bnum, int rnum) {
		return rboardDAO.findbyRnum(bnum, rnum);
	}
	
	/**
	 * 댓글삭제 by 댓글번호, 사용자id
	 */
	@Override
	public List<RboardListReqDTO> del(int bnum, int rnum, String id) {
		boardDAO.downRcount(bnum);
		return rboardDAO.del(bnum, rnum, id);
	}

	/**
	 * 댓글목록조회 by 게시글번호
	 */
	@Override
	public List<RboardListReqDTO> all(int bnum) {
		return rboardDAO.all(bnum);
	}

	
	
}
