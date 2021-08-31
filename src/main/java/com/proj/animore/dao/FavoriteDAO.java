package com.proj.animore.dao;

import java.util.List;

import com.proj.animore.dto.BoardReqDTO;
import com.proj.animore.dto.FavoriteDTO;
import com.proj.animore.form.FavoriteForm;

public interface FavoriteDAO {
	
	
	//즐겨찾기 등록
	public void addFavorite(FavoriteDTO favoriteDTO);
	
	//즐겨찾기 삭제
	public void deleteFavorite(Integer mnum);
	
	//즐겨찾기 목록
	public List<FavoriteDTO> favoritelist(String id);
	
	

}
