package com.szypula.github.githubscreen.detail.domain;

import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel;

import rx.Observable;

public interface UserDetailInteractor {

    Observable<UserDetailDomainModel> getUser();

    Observable<Boolean> toggleFavourite();
}
