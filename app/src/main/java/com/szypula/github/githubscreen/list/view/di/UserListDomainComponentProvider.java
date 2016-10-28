package com.szypula.github.githubscreen.list.view.di;

import com.szypula.github.App;
import com.szypula.github.githubscreen.list.domain.di.UserListDomainComponent;
import com.szypula.github.githubscreen.list.domain.di.UserListDomainModule;

public enum UserListDomainComponentProvider {

    INSTANCE;

    private UserListDomainComponent userListDomainComponent;

    public UserListDomainComponent getUserListDomainComponent() {
        if (userListDomainComponent == null) {
            userListDomainComponent = App.getSingletonComponent().plus(new UserListDomainModule());
        }
        return userListDomainComponent;
    }
}
