package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.MyAniDTO;

public interface MyAniDAO {

	//키우는 동물 등록
	public void registerMyAni(MyAniDTO myAniDTO);
	//키우는 동물 조회
	public List<String> findMyAni(String id);
	//키우는 동물 삭제
	public void lostMyAni(String id);
}
