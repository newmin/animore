package com.proj.animore.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proj.animore.dao.FavoriteDAO;
import com.proj.animore.dto.FavoriteDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class favoriteSVCImpl implements FavoriteSVC {
	
	private final FavoriteDAO favoriteDAO;

	@Override
	public void addFavorite(Integer mnum, FavoriteDTO favoriteDTO) {
		favoriteDAO.addFavorite(favoriteDTO);	
	}

	@Override
	public void deleteBoard(int mnum) {
		favoriteDAO.deleteFavorite(mnum);
	}

	@Override
	public List<FavoriteDTO>favoritelist(String id) {
		return favoriteDAO.favoritelist(id);
	}

}
