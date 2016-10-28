package com.szypula.github.githubscreen.detail.view;

public interface UserDetailMVP {

    interface View {

        void showLogo(String url);

        void showName(String name);

        void showWebsite(String website);

        void hideWebsite();

        void showError(String message);

        void showIsFavourite();

        void showIsNotFavourite();
    }

    interface Presenter {

        void injectView(View view);

        void present();

        void toggleFavourite();
    }
}
