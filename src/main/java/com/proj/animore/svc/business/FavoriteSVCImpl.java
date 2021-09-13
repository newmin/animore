package com.proj.animore.svc.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.business.FavoriteDAO;
import com.proj.animore.dto.business.FavoriteReq;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FavoriteSVCImpl implements FavoriteSVC {
	
	private final FavoriteDAO favoriteDAO;

	//즐겨찾기 등록
	@Override
	public void addFavorite(Integer bnum, String id) {
		favoriteDAO.addFavorite(bnum,id);	
	}

	//즐겨찾기 삭제
	@Override
	public void deleteFavorite(Integer bnum, String id) {
		favoriteDAO.deleteFavorite(bnum,id);
	}

	//즐겨찾기 목록
	@Override
	public List<FavoriteReq>favoriteList(String id) {
		return favoriteDAO.favoriteList(id);
	}
	
	//즐겨찾기 조회
	@Override
//	public int isFavorite(Integer bnum, String id) {
	public FavoriteReq isFavorite(Integer bnum, String id) {
		return favoriteDAO.isFavorite(bnum, id);
	}

}