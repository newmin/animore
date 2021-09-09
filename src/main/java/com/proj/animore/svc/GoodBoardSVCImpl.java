package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.GoodBoardDAO;
import com.proj.animore.dto.GoodBoardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodBoardSVCImpl implements GoodBoardSVC {

	private final GoodBoardDAO goodBoardDAO;
	
	@Override
	public void addGoodBoard(String id, Integer bnum) {
		goodBoardDAO.addGoodBoard(id, bnum);

	}

	@Override
	public void upGoodBoardCnt(Integer bnum) {
		goodBoardDAO.upGoodBoardCnt(bnum);

	}

	@Override
	public List<GoodBoardDTO> goodBoardList(String id) {
		return goodBoardDAO.goodBoardList(id);
	}

	@Override
	public void delGoodBoard(Integer bnum, String id) {
		goodBoardDAO.delGoodBoard(bnum, id);

	}

	@Override
	public void downGoodBoardCnt(Integer bnum) {
		goodBoardDAO.downGoodBoardCnt(bnum);

	}
	@Override
	public int isGoodBoard(Integer bnum, String id) {
		return goodBoardDAO.isGoodBoard(bnum,id);
	}
	@Override
	public int GoodBoardCnt(Integer bnum) {
		return goodBoardDAO.GoodBoardCnt(bnum);
	}
}
