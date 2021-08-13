package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.BusinessDTO;

public interface BusinessDAO {

	//업체등록
	public void joinBusi(BusinessDTO businessDTO);
	
	//업체찾기 by bnum
	public BusinessDTO findBusiByBbnum(String bnum);
	
	//업체수정 by bnum
	public BusinessDTO modifyBusi(String bnum, BusinessDTO businessDTO);
	
	//업체삭제 by bnum
	public void deleteBusi(String bnum);
	
	//업체전체목록
	//TODO 업체카테고리받기 / 텍스트검색으로 출력
	public List<BusinessDTO> list();
}