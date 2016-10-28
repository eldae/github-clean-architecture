package com.szypula.github.githubscreen.detail.domain.di;

import com.szypula.github.githubscreen.detail.domain.UserDetailInteractor;
import com.szypula.github.githubscreen.detail.domain.UserDetailInteractorImpl;
import com.szypula.github.githubscreen.detail.domain.dao.UserDetailDAO;
import com.szypula.github.githubscreen.detail.domain.dao.FavouritesDetailDAO;
import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel;
import com.szypula.github.githubscreen.detail.domain.usecase.GetUserDetailUseCase;
import com.szypula.github.githubscreen.detail.domain.usecase.ToggleFavouriteUseCase;
import com.szypula.github.core.usecase.UseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class UserDetailDomainModule {

    private String code;

    public UserDetailDomainModule(String code) {
        this.code = code;
    }

    @Provides
    @Singleton
    UseCase<UserDetailDomainModel> getUserDetailUseCase(UserDetailDAO dao) {
        return new GetUserDetailUseCase(Schedulers.io(), dao, code);
    }

    @Provides
    @Singleton
    UseCase<Boolean> toggleFavouriteUseCase(FavouritesDetailDAO favouritesDetailDAO) {
        return new ToggleFavouriteUseCase(AndroidSchedulers.mainThread(), favouritesDetailDAO, code);
    }

    @Provides
    @Singleton
    UserDetailInteractor interactor(UseCase<UserDetailDomainModel> getUserDetailUseCase,
                                    UseCase<Boolean> toggleFavouriteUseCase) {
        return new UserDetailInteractorImpl(getUserDetailUseCase, toggleFavouriteUseCase);
    }
}
