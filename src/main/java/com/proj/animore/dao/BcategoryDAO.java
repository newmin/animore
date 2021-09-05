package com.proj.animore.dao;

import com.proj.animore.dto.BcategoryDTO;

public interface BcategoryDAO {

	//업체 카테고리 등록
	public void addBcategory(BcategoryDTO bcategoryDTO);
	
	//업체카테고리 삭제
	public void delBcategory(String bnum);
	
}
