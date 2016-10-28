package com.szypula.github.githubscreen.detail.view.di;

import com.szypula.github.core.di.scope.ViewScope;
import com.szypula.github.githubscreen.detail.domain.di.UserDetailDomainComponent;
import com.szypula.github.githubscreen.detail.view.UserDetailFragment;

import dagger.Component;

@ViewScope
@Component(
        modules = UserDetailViewModule.class,
        dependencies = UserDetailDomainComponent.class
)
public interface UserDetailViewComponent {

    void inject(UserDetailFragment fragment);

}
