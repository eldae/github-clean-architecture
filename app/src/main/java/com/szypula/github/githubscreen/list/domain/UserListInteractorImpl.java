package com.szypula.github.githubscreen.list.domain;

import android.support.annotation.VisibleForTesting;

import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel;
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;
import com.szypula.github.core.usecase.UseCase;

import java.util.List;

import rx.Observable;

public class UserListInteractorImpl implements UserListInteractor {

    private UseCase<List<UserDomainModel>> getAllUsersUseCase;
    private UseCase<List<UserDomainModel>> getFavouriteUsersUseCase;
    private UseCase<FavouriteDomainModel> observeToggleFavouriteUseCase;

    @VisibleForTesting
    boolean isFavouriteOnlyEnabled = false;

    public UserListInteractorImpl(UseCase<List<UserDomainModel>> getAllUsersUseCase,
                                  UseCase<List<UserDomainModel>> getFavouriteUsersUseCase,
                                  UseCase<FavouriteDomainModel> observeToggleFavouriteUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getFavouriteUsersUseCase = getFavouriteUsersUseCase;
        this.observeToggleFavouriteUseCase = observeToggleFavouriteUseCase;
    }

    @Override
    public Observable<List<UserDomainModel>> getUsers() {
        if (isFavouriteOnlyEnabled) {
            return getFavouriteUsersUseCase.execute();
        } else {
            return getAllUsersUseCase.execute();
        }
    }

    @Override
    public void toggleFavouriteEnabled() {
        isFavouriteOnlyEnabled = !isFavouriteOnlyEnabled;
    }

    @Override
    public boolean isFavouriteOnlyEnabled() {
        return isFavouriteOnlyEnabled;
    }

    @Override
    public Observable<FavouriteDomainModel> observeFavouriteChanges() {
        return observeToggleFavouriteUseCase.execute();
    }
}
