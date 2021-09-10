package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.BusinessLoadDTO;
import com.proj.animore.dto.HtagBusiListReq;

public interface BusinessSVC {

	//업체등록
	public void joinBusi(BusinessDTO businessDTO);
	
	//업체찾기 by bnum
	public BusinessLoadDTO findBusiByBnum(Integer bnum);
	
	//업체수정 by bnum
	public BusinessLoadDTO modifyBusi(Integer bnum, BusinessLoadDTO businessLoadDTO);
	
	//업체삭제 by bnum
	public void deleteBusi(Integer bnum);
	
	//업체전체목록
	//TODO 업체카테고리받기 / 텍스트검색으로 출력
	public List<BusinessLoadDTO> busiList(String bcategory);
	
	//회원은 즐겨찾기 목록 상단 고정
	public List<BusinessLoadDTO> busiListForMember(String bcategory, String id);

	//검색어로 목록
	public List<BusinessLoadDTO> busiListBySearch(String search);
	
	//업체목록조회 (병원태그포함)
	public List<BusinessLoadDTO> busiListHospitalTag(String bcategory, HtagBusiListReq htblr);
	
	//내업체 목록 
	public List<BusinessLoadDTO> mybusiList(String id);
	//내체찾기 by ID
	public BusinessLoadDTO findBusiById(String id);

	

}
