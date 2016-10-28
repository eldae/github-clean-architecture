package com.szypula.github.githubscreen.detail.view;

import com.szypula.github.githubscreen.detail.domain.UserDetailInteractor;
import com.szypula.github.githubscreen.detail.view.model.UserDetailViewModel;
import com.szypula.github.githubscreen.detail.view.model.UserDetailViewModelMapper;

import rx.Observer;
import rx.Scheduler;

public class UserDetailPresenter implements UserDetailMVP.Presenter {

    private UserDetailMVP.View view;
    private UserDetailInteractor interactor;
    private UserDetailViewModelMapper mapper;
    private Scheduler uiScheduler;

    public UserDetailPresenter(UserDetailInteractor interactor,
                               UserDetailViewModelMapper mapper,
                               Scheduler uiScheduler) {
        this.interactor = interactor;
        this.mapper = mapper;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void injectView(UserDetailMVP.View view) {
        this.view = view;
    }

    @Override
    public void present() {
        interactor.getUser()
                  .map(userDetailDomainModel -> mapper.map(userDetailDomainModel))
                  .observeOn(uiScheduler)
                  .subscribe(new UserDetailObserver());
    }

    @Override
    public void toggleFavourite() {
        interactor.toggleFavourite()
                  .doOnNext(this::toggleIsFavouriteView)
                  .doOnError(error -> view.showError(error.getMessage()))
                  .subscribe();
    }

    private void toggleIsFavouriteView(boolean isFavourite) {
        if (isFavourite) {
            view.showIsFavourite();
        } else {
            view.showIsNotFavourite();
        }
    }

    private class UserDetailObserver implements Observer<UserDetailViewModel> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.getMessage());
        }

        @Override
        public void onNext(UserDetailViewModel userDomainModel) {
            view.showLogo(userDomainModel.getLogoUrl());
            view.showName(userDomainModel.getName());
            presentWebsite(userDomainModel.getSite());
            toggleIsFavouriteView(userDomainModel.isFavourite());
        }

        private void presentWebsite(String website) {
            if (website == null || website.isEmpty()) {
                view.hideWebsite();
            } else {
                view.showWebsite(website);
            }
        }
    }
}
