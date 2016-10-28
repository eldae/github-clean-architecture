package com.szypula.github.githubscreen.list.domain.usecase;

import com.szypula.github.githubscreen.list.domain.dao.FavouriteDAO;
import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel;
import com.szypula.github.core.usecase.AbstractUseCase;

import rx.Observable;
import rx.Scheduler;

public class ObserveToggleFavouriteUseCase extends AbstractUseCase<FavouriteDomainModel> {

    private FavouriteDAO favouriteDAO;

    public ObserveToggleFavouriteUseCase(Scheduler executionScheduler, FavouriteDAO favouriteDAO) {
        super(executionScheduler);
        this.favouriteDAO = favouriteDAO;
    }

    @Override
    protected Observable<FavouriteDomainModel> emit() {
        return favouriteDAO.favourite();
    }
}
