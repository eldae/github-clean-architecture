package com.szypula.github.githubscreen.detail.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.szypula.github.R;
import com.szypula.github.githubscreen.detail.domain.di.UserDetailDomainComponent;
import com.szypula.github.githubscreen.detail.view.di.DaggerUserDetailViewComponent;
import com.szypula.github.githubscreen.detail.view.di.UserDetailDomainComponentProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailFragment extends Fragment implements UserDetailMVP.View {

    private static final String ARG_CODE = "code";

    @BindView(R.id.logo) ImageView logo;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.website) TextView website;
    @BindView(R.id.favourite) FloatingActionButton favourite;

    @Inject UserDetailMVP.Presenter presenter;

    public static UserDetailFragment getInstance(String code) {
        UserDetailFragment userDetailFragment = new UserDetailFragment();
        Bundle arguments = new Bundle(1);
        arguments.putString(ARG_CODE, code);
        userDetailFragment.setArguments(arguments);
        return userDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().containsKey(ARG_CODE)) {
            throw new IllegalArgumentException("You have to provide code");
        }
        initDependencies();
    }

    private void initDependencies() {
        String code = getArguments().getString(ARG_CODE);
        UserDetailDomainComponent domainComponent =
                UserDetailDomainComponentProvider.INSTANCE.getUserDetailDomainComponent(code);
        DaggerUserDetailViewComponent.builder()
                                     .userDetailDomainComponent(domainComponent)
                                     .build()
                                     .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.injectView(this);
        presenter.present();
    }

    @Override
    public void showLogo(String url) {
        Glide.with(this)
             .load(url)
             .error(R.drawable.ic_photo_black_24px)
             .fitCenter()
             .into(logo);
    }

    @Override
    public void showName(String name) {
        this.name.setText(name);
    }

    @Override
    public void showWebsite(String website) {
        this.website.setText(website);
    }

    @Override
    public void hideWebsite() {
        website.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(name.getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showIsFavourite() {
        favourite.setImageResource(android.R.drawable.star_on);
    }

    @Override
    public void showIsNotFavourite() {
        favourite.setImageResource(android.R.drawable.star_off);
    }

    @OnClick(R.id.favourite)
    void toggleFavourite() {
        presenter.toggleFavourite();
    }
}
