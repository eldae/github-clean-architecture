package com.szypula.github.githubscreen.list.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.szypula.github.R;
import com.szypula.github.githubscreen.list.view.model.UserViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsersRecyclerViewAdapter
        extends RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder> {

    private final List<UserViewModel> users;
    private UserListFragment.OnUserClickListener onUserClickListener;

    public UsersRecyclerViewAdapter(UserListFragment.OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
        this.users = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserViewModel user = users.get(position);
        holder.user = user;
        holder.name.setText(user.getName());
        Glide.with(holder.view.getContext())
             .load(user.getLogoUrl())
             .error(R.drawable.ic_photo_black_24px)
             .fitCenter()
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .into(holder.logo);
        holder.view.setOnClickListener(view -> onUserClickListener.onUserClick(user.getCode()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setItems(Collection<UserViewModel> users) {
        this.users.clear();
        this.users.addAll(users);
    }

    public List<UserViewModel> getUsers() {
        return users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final ImageView logo;
        public final TextView name;
        public UserViewModel user;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.name = ((TextView) view.findViewById(R.id.name));
            this.logo = ((ImageView) view.findViewById(R.id.logo));
        }
    }
}
