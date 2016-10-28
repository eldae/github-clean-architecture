package com.szypula.github.core.di;

import com.szypula.github.githubscreen.detail.domain.di.UserDetailDomainComponent;
import com.szypula.github.githubscreen.detail.domain.di.UserDetailDomainModule;
import com.szypula.github.githubscreen.list.domain.di.UserListDomainComponent;
import com.szypula.github.githubscreen.list.domain.di.UserListDomainModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SingletonModule.class)
public interface SingletonComponent {

    UserListDomainComponent plus(UserListDomainModule module);

    UserDetailDomainComponent plus(UserDetailDomainModule module);
}
