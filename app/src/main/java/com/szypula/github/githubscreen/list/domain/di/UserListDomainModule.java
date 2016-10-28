package com.szypula.github.githubscreen.list.domain.di;

import com.szypula.github.core.usecase.UseCase;
import com.szypula.github.githubscreen.list.domain.UserListInteractor;
import com.szypula.github.githubscreen.list.domain.UserListInteractorImpl;
import com.szypula.github.githubscreen.list.domain.dao.FavouriteDAO;
import com.szypula.github.githubscreen.list.domain.dao.UsersDAO;
import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel;
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;
import com.szypula.github.githubscreen.list.domain.usecase.GetAllUsersUseCase;
import com.szypula.github.githubscreen.list.domain.usecase.GetFavouriteUsersUseCase;
import com.szypula.github.githubscreen.list.domain.usecase.ObserveToggleFavouriteUseCase;

import java.util.List;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class UserListDomainModule {

    @Provides
    @Singleton
    @All
    UseCase<List<UserDomainModel>> allUseCase(UsersDAO usersDAO) {
        return new GetAllUsersUseCase(Schedulers.io(), usersDAO);
    }

    @Provides
    @Singleton
    @Favourite
    UseCase<List<UserDomainModel>> favouriteUseCase(UsersDAO usersDAO) {
        return new GetFavouriteUsersUseCase(Schedulers.io(), usersDAO);
    }

    @Provides
    @Singleton
    UseCase<FavouriteDomainModel> observeToggleFavouriteUseCase(FavouriteDAO favouriteDAO) {
        return new ObserveToggleFavouriteUseCase(AndroidSchedulers.mainThread(), favouriteDAO);
    }

    @Provides
    @Singleton
    UserListInteractor userListInteractor(@All UseCase<List<UserDomainModel>> getAllUsers,
                                          @Favourite UseCase<List<UserDomainModel>> getFavouriteUsers,
                                          UseCase<FavouriteDomainModel> observeToggleFavouriteUseCase) {
        return new UserListInteractorImpl(getAllUsers, getFavouriteUsers, observeToggleFavouriteUseCase);
    }

    @Qualifier
    public @interface All {
    }

    @Qualifier
    public @interface Favourite {
    }

}
