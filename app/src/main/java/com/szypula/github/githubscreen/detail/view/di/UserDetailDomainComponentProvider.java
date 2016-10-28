package com.szypula.github.githubscreen.detail.view.di;

import com.szypula.github.App;
import com.szypula.github.githubscreen.detail.domain.di.UserDetailDomainComponent;
import com.szypula.github.githubscreen.detail.domain.di.UserDetailDomainModule;

public enum UserDetailDomainComponentProvider {

    INSTANCE;

    private String currentCode;
    private UserDetailDomainComponent userDetailDomainComponent;

    public UserDetailDomainComponent getUserDetailDomainComponent(String code) {
        if (userDetailDomainComponent == null || !code.equals(currentCode)) {
            currentCode = code;
            userDetailDomainComponent = App.getSingletonComponent().plus(new UserDetailDomainModule(code));
        }
        return userDetailDomainComponent;
    }
}
