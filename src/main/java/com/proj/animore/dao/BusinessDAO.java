package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.BusinessDTO;

public interface BusinessDAO {

	public void joinBusi(BusinessDTO businessDTO);
	public BusinessDTO findBusiByBbnum(String bnum);
	public BusinessDTO modifyBusi(String bnum, BusinessDTO businessDTO);
	public void deleteBusi(String bnum);
	//TODO 업체카테고리받기 / 텍스트검색으로 출력
	public List<BusinessDTO> list();
}
