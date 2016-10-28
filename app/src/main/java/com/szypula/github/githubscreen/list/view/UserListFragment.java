package com.szypula.github.githubscreen.list.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.szypula.github.R;
import com.szypula.github.githubscreen.list.domain.di.UserListDomainComponent;
import com.szypula.github.githubscreen.list.view.di.DaggerUserListViewComponent;
import com.szypula.github.githubscreen.list.view.di.UserListDomainComponentProvider;
import com.szypula.github.githubscreen.list.view.di.UserListViewModule;
import com.szypula.github.githubscreen.list.view.model.UserViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListFragment extends Fragment implements UserListMVP.View {

    @Inject UserListMVP.Presenter presenter;

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.list) RecyclerView recyclerView;

    private OnUserClickListener onUserClickListener;
    private UsersRecyclerViewAdapter adapter;
    private int favouriteDrawableRes = android.R.drawable.star_big_off;
    private MenuItem menuItemFavourite;

    public static UserListFragment getInstance() {
        return new UserListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initDependencies();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_favorite) == null) {
            inflater.inflate(R.menu.list_menu, menu);
            menuItemFavourite = menu.findItem(R.id.action_favorite);
            menuItemFavourite.setIcon(favouriteDrawableRes);
        }
    }

    private void initDependencies() {
        UserListDomainComponent domainComponent =
                UserListDomainComponentProvider.INSTANCE.getUserListDomainComponent();
        DaggerUserListViewComponent.builder()
                                   .userListDomainComponent(domainComponent)
                                   .userListViewModule(new UserListViewModule())
                                   .build()
                                   .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list, container, false);
        ButterKnife.bind(this, view);
        adapter = new UsersRecyclerViewAdapter(onUserClickListener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.injectView(this);
        presenter.present();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onUserClickListener = ((OnUserClickListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnUserClickListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.handleMenuClick(item.getItemId());
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemsAdded() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showUserList(List<UserViewModel> users) {
        adapter.setItems(users);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void turnFavouriteIconOn() {
        favouriteDrawableRes = android.R.drawable.star_big_on;
        if (menuItemFavourite != null) {
            menuItemFavourite.setIcon(favouriteDrawableRes);
        }
    }

    @Override
    public void turnFavouriteIconOff() {
        favouriteDrawableRes = android.R.drawable.star_big_off;
        if (menuItemFavourite != null) {
            menuItemFavourite.setIcon(favouriteDrawableRes);
        }
    }


    @Override
    public void onItemAdded(int position) {
        recyclerView.smoothScrollToPosition(position);
        adapter.notifyItemInserted(position);
    }

    @Override
    public void onItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public List<UserViewModel> getCurrentList() {
        return adapter.getUsers();
    }

    public interface OnUserClickListener {
        void onUserClick(String code);
    }
}
