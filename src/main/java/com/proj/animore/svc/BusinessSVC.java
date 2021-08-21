package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dto.BusinessLoadDTO;

public interface BusinessSVC {

	//업체목록 조회
	List<BusinessLoadDTO> busiList(String bcategory);
}
