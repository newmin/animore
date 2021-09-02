package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.FavoriteDTO;

public interface FavoriteDAO {
	
	
	//즐겨찾기 등록
	public void addFavorite(Integer bnum, String id);
	
	//즐겨찾기 삭제
	public void deleteFavorite(Integer bnum, String id);

	//즐겨찾기 목록
	public List<FavoriteDTO> favoriteList(String id);
	

}
