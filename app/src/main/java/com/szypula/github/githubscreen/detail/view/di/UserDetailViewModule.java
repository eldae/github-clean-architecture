package com.szypula.github.githubscreen.detail.view.di;

import com.szypula.github.core.di.scope.ViewScope;
import com.szypula.github.githubscreen.detail.domain.UserDetailInteractor;
import com.szypula.github.githubscreen.detail.view.UserDetailMVP;
import com.szypula.github.githubscreen.detail.view.UserDetailPresenter;
import com.szypula.github.githubscreen.detail.view.model.UserDetailViewModelMapper;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class UserDetailViewModule {

    @Provides
    @ViewScope
    UserDetailMVP.Presenter presenter(UserDetailInteractor interactor) {
        UserDetailViewModelMapper mapper = new UserDetailViewModelMapper();
        return new UserDetailPresenter(interactor, mapper, AndroidSchedulers.mainThread());
    }
}
