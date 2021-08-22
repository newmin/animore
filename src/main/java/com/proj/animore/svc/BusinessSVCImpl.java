package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.BusinessDAO;
import com.proj.animore.dto.BusinessLoadDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessSVCImpl implements BusinessSVC {

	private final BusinessDAO businessDAO;
	
	
	//업체목록 조회
	@Override
	public List<BusinessLoadDTO> busiList(String bcategory) {
		return businessDAO.list(bcategory);
	}

}