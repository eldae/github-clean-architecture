package com.szypula.github.githubscreen.detail.domain;

import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel;
import com.szypula.github.core.usecase.UseCase;

import rx.Observable;

public class UserDetailInteractorImpl implements UserDetailInteractor {

    private UseCase<UserDetailDomainModel> getUserDetailUseCase;
    private UseCase<Boolean> toggleFavouriteUseCase;

    public UserDetailInteractorImpl(UseCase<UserDetailDomainModel> getUserDetailUseCase,
                                    UseCase<Boolean> toggleFavouriteUseCase) {
        this.getUserDetailUseCase = getUserDetailUseCase;
        this.toggleFavouriteUseCase = toggleFavouriteUseCase;
    }

    @Override
    public Observable<UserDetailDomainModel> getUser() {
        return getUserDetailUseCase.execute();
    }

    @Override
    public Observable<Boolean> toggleFavourite() {
        return toggleFavouriteUseCase.execute();
    }
}
