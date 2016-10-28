package com.szypula.github.githubscreen.detail.domain.di;

import com.szypula.github.githubscreen.detail.domain.UserDetailInteractor;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = UserDetailDomainModule.class)
public interface UserDetailDomainComponent {

    UserDetailInteractor interactor();
}
