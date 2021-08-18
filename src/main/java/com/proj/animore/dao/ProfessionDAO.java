package com.proj.animore.dao;

import com.proj.animore.dto.ProfessionDTO;

public interface ProfessionDAO {

	//전문가 등록
	public void addprofession(ProfessionDTO professionDTO);
	
	//전문가 조회 by id
	public ProfessionDTO findProByPnum(String id);
	
	//전문가 수정 by id
	public ProfessionDTO modifyProBypnum(String id,ProfessionDTO professionDTO);
	
	//전문가 삭제 by id
	public void deleteProBypnum(String id);

}
