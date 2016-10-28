package com.szypula.github.githubscreen.list.view;

import com.szypula.github.githubscreen.list.view.model.UserViewModel;

import java.util.List;

public interface UserListMVP {

    interface View {

        void showLoading();

        void hideLoading();

        void onItemsAdded();

        void showUserList(List<UserViewModel> users);

        void showError(String message);

        void turnFavouriteIconOn();

        void turnFavouriteIconOff();

        void onItemAdded(int position);

        void onItemRemoved(int position);

        List<UserViewModel> getCurrentList();
    }

    interface Presenter {

        void injectView(View view);

        void present();

        void unsubscribe();

        boolean handleMenuClick(int menuItemId);
    }
}
