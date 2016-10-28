package com.szypula.github.githubscreen.list.domain;

import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;
import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel;

import java.util.List;

import rx.Observable;

public interface UserListInteractor {

    Observable<List<UserDomainModel>> getUsers();

    void toggleFavouriteEnabled();

    boolean isFavouriteOnlyEnabled();

    Observable<FavouriteDomainModel> observeFavouriteChanges();
}
