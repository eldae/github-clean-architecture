package com.szypula.github.core.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.szypula.github.githubscreen.detail.domain.dao.UserDetailDAO;
import com.szypula.github.githubscreen.detail.domain.dao.FavouritesDetailDAO;
import com.szypula.github.githubscreen.list.domain.dao.UsersDAO;
import com.szypula.github.githubscreen.list.domain.dao.FavouriteDAO;
import com.szypula.github.core.Constants;
import com.szypula.github.data.InMemoryUsersDAO;
import com.szypula.github.data.PreferencesFavouritesDAO;
import com.szypula.github.data.mapper.UserMapper;
import com.szypula.github.network.RestAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class SingletonModule {

    private Context applicationContext;
    private InMemoryUsersDAO inMemoryUsersDAO;
    private PreferencesFavouritesDAO favouritesDAO;

    public SingletonModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    RestAPI restAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.MAIN_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        return retrofit.create(RestAPI.class);
    }

    @Provides
    @Singleton
    UserMapper userMapper() {
        return new UserMapper(getPreferencesFavouritesDAO());
    }

    @Provides
    @Singleton
    FavouritesDetailDAO favouritesDetailDAO() {
        return getPreferencesFavouritesDAO();
    }

    @Provides
    @Singleton
    FavouriteDAO favouriteDAO() {
        return getPreferencesFavouritesDAO();
    }

    @NonNull
    private PreferencesFavouritesDAO getPreferencesFavouritesDAO() {
        if (favouritesDAO == null) {
            favouritesDAO = new PreferencesFavouritesDAO(applicationContext.getSharedPreferences("SP", 0));
        }
        return favouritesDAO;
    }

    @Provides
    @Singleton
    UsersDAO usersDAO(RestAPI restAPI, UserMapper userMapper) {
        return getUsersDAO(restAPI, userMapper);
    }

    @Provides
    @Singleton
    UserDetailDAO usersDetailDAO(RestAPI restAPI, UserMapper userMapper) {
        return getUsersDAO(restAPI, userMapper);
    }

    @NonNull
    private InMemoryUsersDAO getUsersDAO(RestAPI restAPI, UserMapper userMapper) {
        if (inMemoryUsersDAO == null) {
            inMemoryUsersDAO = new InMemoryUsersDAO(restAPI, userMapper);
        }
        return inMemoryUsersDAO;
    }
}
