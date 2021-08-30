package com.proj.animore.svc;

import java.util.List;

import com.proj.animore.dao.FavoriteDAO;
import com.proj.animore.dto.FavoriteDTO;

public class favoriteSVCImpl implements FavoriteSVC {
	
	private FavoriteDAO favoriteDAO;

	@Override
	public void addFavorite(Integer mnum, FavoriteDTO favoriteDTO) {
		favoriteDAO.addFavorite(favoriteDTO);	
	}

	@Override
	public void deleteBoard(int mnum) {
		favoriteDAO.deleteFavorite(mnum);
	}

	@Override
	public List<FavoriteDTO> list(Integer mnum) {
		// TODO Auto-generated method stub
		return favoriteDAO.list(mnum);
	}

}
