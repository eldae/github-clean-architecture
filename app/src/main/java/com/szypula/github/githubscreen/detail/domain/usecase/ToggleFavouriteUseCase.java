package com.szypula.github.githubscreen.detail.domain.usecase;

import com.szypula.github.githubscreen.detail.domain.dao.FavouritesDetailDAO;
import com.szypula.github.core.usecase.AbstractUseCase;

import rx.Observable;
import rx.Scheduler;

public class ToggleFavouriteUseCase extends AbstractUseCase<Boolean> {

    private FavouritesDetailDAO favouritesDetailDAO;
    private String code;

    public ToggleFavouriteUseCase(Scheduler executionScheduler,
                                  FavouritesDetailDAO favouritesDetailDAO,
                                  String code) {
        super(executionScheduler);
        this.favouritesDetailDAO = favouritesDetailDAO;
        this.code = code;
    }

    @Override
    protected Observable<Boolean> emit() {
        boolean isFavourite = favouritesDetailDAO.isFavourite(code);
        if (isFavourite) {
            favouritesDetailDAO.removeFavourite(code);
        } else {
            favouritesDetailDAO.addFavourite(code);
        }
        return Observable.just(!isFavourite);
    }
}
