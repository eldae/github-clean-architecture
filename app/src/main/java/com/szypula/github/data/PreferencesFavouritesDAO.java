package com.szypula.github.data;

import android.content.SharedPreferences;

import com.szypula.github.githubscreen.detail.domain.dao.FavouritesDetailDAO;
import com.szypula.github.githubscreen.list.domain.dao.FavouriteDAO;
import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel;

import java.util.HashSet;
import java.util.Set;

import rx.Observable;
import rx.subjects.PublishSubject;

public class PreferencesFavouritesDAO implements FavouritesDetailDAO, FavouriteDAO {

    private final static String FAV_SET = "favSet";

    private SharedPreferences sharedPreferences;
    private PublishSubject<FavouriteDomainModel> subject = PublishSubject.create();

    public PreferencesFavouritesDAO(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public boolean isFavourite(String code) {
        Set<String> favourites = sharedPreferences.getStringSet(FAV_SET, new HashSet<>());
        return favourites.contains(code);
    }

    @Override
    public void addFavourite(String code) {
        Set<String> favourites = sharedPreferences.getStringSet(FAV_SET, new HashSet<>());
        Set<String> newFavourites = new HashSet<>(favourites);
        newFavourites.add(code);
        sharedPreferences.edit().putStringSet(FAV_SET, newFavourites).apply();

        subject.onNext(new FavouriteDomainModel(code, true));
    }

    @Override
    public void removeFavourite(String code) {
        Set<String> favourites = sharedPreferences.getStringSet(FAV_SET, new HashSet<>());
        Set<String> newFavourites = new HashSet<>(favourites);
        newFavourites.remove(code);
        sharedPreferences.edit().putStringSet(FAV_SET, newFavourites).apply();

        subject.onNext(new FavouriteDomainModel(code, false));
    }

    public Set<String> getFavourites() {
        return sharedPreferences.getStringSet(FAV_SET, new HashSet<>());
    }

    @Override
    public Observable<FavouriteDomainModel> favourite() {
        return subject;
    }
}
