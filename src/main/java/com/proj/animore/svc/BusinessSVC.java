package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.BusinessLoadDTO;

public interface BusinessSVC {

	//업체등록
	public void joinBusi(BusinessDTO businessDTO);
	
	//업체찾기 by bnum
	public BusinessDTO findBusiByBnum(Integer bnum);
	
	//업체수정 by bnum
	public BusinessDTO modifyBusi(Integer bnum, BusinessDTO businessDTO);
	
	//업체삭제 by bnum
	public void deleteBusi(Integer bnum);
	
	//업체전체목록
	//TODO 업체카테고리받기 / 텍스트검색으로 출력
	public List<BusinessLoadDTO> busiList(String bcategory);
}
