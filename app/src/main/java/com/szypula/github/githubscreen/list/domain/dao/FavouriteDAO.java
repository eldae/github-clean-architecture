package com.szypula.github.githubscreen.list.domain.dao;

import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel;

import rx.Observable;

public interface FavouriteDAO {

    Observable<FavouriteDomainModel> favourite();
}
