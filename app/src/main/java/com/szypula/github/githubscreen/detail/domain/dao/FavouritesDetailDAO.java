package com.szypula.github.githubscreen.detail.domain.dao;

public interface FavouritesDetailDAO {

    boolean isFavourite(String code);

    void addFavourite(String code);

    void removeFavourite(String code);
}
