package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.BusinessDAO;
import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.BusinessLoadDTO;
import com.proj.animore.dto.HtagBusiListReq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessSVCImpl implements BusinessSVC {

	private final BusinessDAO businessDAO;
	
	@Override
	public void joinBusi(BusinessDTO businessDTO) {
		businessDAO.joinBusi(businessDTO);
		
	}
	@Override
	public BusinessLoadDTO findBusiByBnum(Integer bnum) {
		return businessDAO.findBusiByBnum(bnum);
	}
	@Override
	public BusinessLoadDTO modifyBusi(Integer bnum, BusinessDTO businessDTO) {
		return businessDAO.modifyBusi(bnum, businessDTO);
	}
	@Override
	public void deleteBusi(Integer bnum) {
		businessDAO.deleteBusi(bnum);		
	}
	
	//업체목록 조회
	@Override
	public List<BusinessLoadDTO> busiList(String bcategory) {
		return businessDAO.busiList(bcategory);
	}
	//즐겨찾기 상단고정하여 목록조회
	@Override
	public List<BusinessLoadDTO> busiListForMember(String bcategory, String id) {
		return businessDAO.busiListForMember(bcategory, id);
	}
	//검색어로 목록 조회
	@Override
	public List<BusinessLoadDTO> busiListBySearch(String text) {
		return businessDAO.busiListBySearch(text);
	}

	//업체목록조회 (병원태그포함)
	@Override
	public List<BusinessLoadDTO> busiListHospitalTag(String bcategory, HtagBusiListReq htblr) {
		return businessDAO.busiListHospitalTag(bcategory, htblr);
	}
	
}
