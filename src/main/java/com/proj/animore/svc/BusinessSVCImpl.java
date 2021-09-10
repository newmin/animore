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
	//내업체 수정
	@Override
	public BusinessLoadDTO modifyBusi(String id, BusinessDTO businessDTO) {
		return businessDAO.modifyBusi(id, businessDTO);
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
	public List<BusinessLoadDTO> busiListBySearch(String search) {
		return businessDAO.busiListBySearch(search);
	}
	@Override
	public List<BusinessLoadDTO> mybusiList(String id){
		return  businessDAO.mybusiList(id);
	}

	//업체목록조회 (병원태그포함)
	@Override
	public List<BusinessLoadDTO> busiListHospitalTag(String bcategory, HtagBusiListReq htblr) {
		return businessDAO.busiListHospitalTag(bcategory, htblr);
	}
	//내업체 찾기
	@Override
	public BusinessLoadDTO findBusiById(String id) {
		return businessDAO.findBusiById(id);
	}
	
}
