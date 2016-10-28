package com.szypula.github.githubscreen.list.domain.di;

import com.szypula.github.githubscreen.list.domain.UserListInteractor;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = UserListDomainModule.class)
public interface UserListDomainComponent {

    UserListInteractor userListInteractor();
}
