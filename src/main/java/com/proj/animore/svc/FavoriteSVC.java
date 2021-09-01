package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dto.FavoriteDTO;

public interface FavoriteSVC {
	
	//즐겨찾기 추가
	void addFavorite(Integer mnum,FavoriteDTO favoriteDTO);
	
	//즐겨찾기 삭제
	void deleteBoard(int mnum);
	
	//즐겨찾기 목록
	List<FavoriteDTO>favoritelist(String id);


}