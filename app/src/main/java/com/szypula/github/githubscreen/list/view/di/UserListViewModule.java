package com.szypula.github.githubscreen.list.view.di;

import com.szypula.github.core.di.scope.ViewScope;
import com.szypula.github.githubscreen.list.domain.UserListInteractor;
import com.szypula.github.githubscreen.list.view.UserListMVP;
import com.szypula.github.githubscreen.list.view.UserListPresenter;
import com.szypula.github.githubscreen.list.view.model.UserViewModelMapper;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class UserListViewModule {

    @Provides
    @ViewScope
    UserListMVP.Presenter presenter(UserListInteractor interactor) {
        UserViewModelMapper mapper = new UserViewModelMapper();
        return new UserListPresenter(interactor, mapper, AndroidSchedulers.mainThread());
    }

}
