package com.szypula.github.githubscreen.list.view;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.szypula.github.R;
import com.szypula.github.githubscreen.list.domain.UserListInteractor;
import com.szypula.github.githubscreen.list.domain.model.FavouriteDomainModel;
import com.szypula.github.githubscreen.list.view.model.UserViewModel;
import com.szypula.github.githubscreen.list.view.model.UserViewModelMapper;

import java.util.List;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

public class UserListPresenter implements UserListMVP.Presenter {

    @VisibleForTesting
    Subscription getUsersSubscription = Subscriptions.empty();
    @VisibleForTesting
    Subscription favouriteChangesSubscription = Subscriptions.empty();

    private UserListMVP.View view;
    private UserListInteractor interactor;
    private UserViewModelMapper mapper;
    private Scheduler uiScheduler;

    public UserListPresenter(UserListInteractor interactor,
                             UserViewModelMapper mapper,
                             Scheduler uiScheduler) {
        this.interactor = interactor;
        this.mapper = mapper;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void injectView(UserListMVP.View view) {
        this.view = view;
    }

    @Override
    public void present() {
        updateFavouriteIcon();
        view.showLoading();
        getUsersSubscription = interactor.getUsers()
                                         .map(userDomainModels -> mapper.map(userDomainModels))
                                         .observeOn(uiScheduler)
                                         .subscribe(new UserListObserver());
        favouriteChangesSubscription = interactor.observeFavouriteChanges()
                                                 .observeOn(uiScheduler)
                                                 .subscribe(new ToggleFavouriteObserver());
    }

    private void updateFavouriteIcon() {
        if (interactor.isFavouriteOnlyEnabled()) {
            view.turnFavouriteIconOn();
        } else {
            view.turnFavouriteIconOff();
        }
    }

    @Override
    public boolean handleMenuClick(int menuItemId) {
        if (menuItemId == R.id.action_favorite) {
            interactor.toggleFavouriteEnabled();
            updateFavouriteIcon();
            getUsersSubscription = interactor.getUsers()
                                             .map(userDomainModels -> mapper.map(userDomainModels))
                                             .observeOn(uiScheduler)
                                             .subscribe(new UserListObserver());
            return true;
        }
        return false;
    }

    @Override
    public void unsubscribe() {
        if (!getUsersSubscription.isUnsubscribed()) {
            getUsersSubscription.unsubscribe();
        }
        if (!favouriteChangesSubscription.isUnsubscribed()) {
            favouriteChangesSubscription.unsubscribe();
        }
    }

    private class UserListObserver implements Observer<List<UserViewModel>> {

        @Override
        public void onCompleted() {
            view.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            view.hideLoading();
            view.showError(e.getMessage());
        }

        @Override
        public void onNext(List<UserViewModel> userDomainModels) {
            view.showUserList(userDomainModels);
            view.onItemsAdded();
        }
    }

    private class ToggleFavouriteObserver implements Observer<FavouriteDomainModel> {

        private ListIndexCalculator listIndexCalculator = new ListIndexCalculator();

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.getMessage());
        }

        @Override
        public void onNext(FavouriteDomainModel favouriteDomainModel) {
            if (interactor.isFavouriteOnlyEnabled()) {
                Action1<List<UserViewModel>> action;
                if (favouriteDomainModel.isFavourite()) {
                    action = addItem(favouriteDomainModel);
                } else {
                    action = removeItem(favouriteDomainModel);
                }
                getUsersSubscription = interactor.getUsers()
                                                 .map(userDomainModels -> mapper.map(userDomainModels))
                                                 .observeOn(uiScheduler)
                                                 .doOnNext(action)
                                                 .doOnError(error -> view.showError(error.getMessage()))
                                                 .subscribe();
            }
        }

        @NonNull
        private Action1<List<UserViewModel>> removeItem(FavouriteDomainModel favouriteDomainModel) {
            return userViewModels -> {
                int position = listIndexCalculator.indexOfRemoved(view.getCurrentList(), favouriteDomainModel.getCode());
                view.showUserList(userViewModels);
                view.onItemRemoved(position);
            };
        }

        @NonNull
        private Action1<List<UserViewModel>> addItem(FavouriteDomainModel favouriteDomainModel) {
            return userViewModels -> {
                int position = listIndexCalculator.indexOf(userViewModels,
                                                           favouriteDomainModel.getCode());
                view.showUserList(userViewModels);
                view.onItemAdded(position);
            };
        }
    }
}
