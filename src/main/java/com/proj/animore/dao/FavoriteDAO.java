package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.FavoriteReq;

public interface FavoriteDAO {
	
	
	//즐겨찾기 등록
	public void addFavorite(Integer bnum, String id);
	
	//즐겨찾기 삭제
	public void deleteFavorite(Integer bnum, String id);

	//즐겨찾기 목록
	public List<FavoriteReq> favoriteList(String id);
	
  //즐겨찾기 조회
	public FavoriteReq isFavorite(Integer bnum, String id);
//	public int isFavorite(Integer bnum, String id);
}
