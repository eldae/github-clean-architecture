package com.szypula.github.githubscreen.list.view.di;

import com.szypula.github.core.di.scope.ViewScope;
import com.szypula.github.githubscreen.list.domain.di.UserListDomainComponent;
import com.szypula.github.githubscreen.list.view.UserListFragment;

import dagger.Component;

@ViewScope
@Component(
        modules = UserListViewModule.class,
        dependencies = UserListDomainComponent.class
)
public interface UserListViewComponent {

    void inject(UserListFragment fragment);
}
