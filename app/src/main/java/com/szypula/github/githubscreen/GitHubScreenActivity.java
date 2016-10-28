package com.szypula.github.githubscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.szypula.github.R;
import com.szypula.github.githubscreen.detail.view.UserDetailFragment;
import com.szypula.github.githubscreen.list.view.UserListFragment;

import butterknife.ButterKnife;

public class GitHubScreenActivity extends AppCompatActivity implements UserListFragment.OnUserClickListener {

    private static final String ARG_CODE = "code";

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(savedInstanceState);
    }

    private void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.github_screen);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getTitle());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            showList(fragmentManager);
        } else if (isTwoPane() && wasOnePaneBeforeConfigChange(fragmentManager)) {
            clearBackStack(fragmentManager);
            String currentDetailCode = savedInstanceState.getString(ARG_CODE);
            showDetailNow(fragmentManager, currentDetailCode);
        }

    }

    private void showList(FragmentManager fragmentManager) {
        UserListFragment list = UserListFragment.getInstance();
        fragmentManager.beginTransaction()
                       .replace(R.id.list_container, list)
                       .commit();
    }

    @SuppressLint("CommitTransaction")
    private void showDetailNow(FragmentManager fragmentManager, String code) {
        UserDetailFragment detail = UserDetailFragment.getInstance(code);
        fragmentManager.beginTransaction()
                       .replace(R.id.detail_container, detail)
                       .commitNow();
    }

    private boolean isTwoPane() {
        return getResources().getBoolean(R.bool.isTwoPane);
    }

    private boolean wasOnePaneBeforeConfigChange(FragmentManager fragmentManager) {
        return fragmentManager.findFragmentById(R.id.list_container) instanceof UserDetailFragment;
    }

    private void clearBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_CODE, code);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onUserClick(String code) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isTwoPane() && !code.equals(this.code)) {
            this.code = code;
            showDetail(fragmentManager, code);
        } else {
            showDetailOnBackStack(fragmentManager, code);
        }
    }

    private void showDetail(FragmentManager fragmentManager, String code) {
        UserDetailFragment detail = UserDetailFragment.getInstance(code);
        fragmentManager.beginTransaction()
                       .replace(R.id.detail_container, detail)
                       .commit();
    }

    private void showDetailOnBackStack(FragmentManager fragmentManager, String code) {
        UserDetailFragment detail = UserDetailFragment.getInstance(code);
        fragmentManager.beginTransaction()
                       .replace(R.id.list_container, detail, "detail")
                       .addToBackStack("detail")
                       .commit();
    }
}
