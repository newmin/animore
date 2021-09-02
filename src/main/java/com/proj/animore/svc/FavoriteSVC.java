package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dto.FavoriteDTO;

public interface FavoriteSVC {
	
	//즐겨찾기 등록
	public void addFavorite(Integer bnum, String id);
	
	//즐겨찾기 삭제
	public void deleteFavorite(Integer bnum, String id);

	//즐겨찾기 목록
	public List<FavoriteDTO> favoriteList(String id);


}