package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.MyAniDAO;
import com.proj.animore.dto.MyAniDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyAniSVCImpl implements MyAniSVC {
	
	private final MyAniDAO myAniDAO;

	@Override
	public void registerMyAni(MyAniDTO myAniDTO) {
		myAniDAO.registerMyAni(myAniDTO);
	}

	@Override
	public List<String> findMyAni(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lostMyAni(String id) {
		// TODO Auto-generated method stub

	}

}
