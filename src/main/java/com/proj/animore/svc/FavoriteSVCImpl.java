package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.FavoriteDAO;
import com.proj.animore.dto.FavoriteDTO;

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
	public List<FavoriteDTO>favoriteList(String id) {
		return favoriteDAO.favoriteList(id);
	}

}
