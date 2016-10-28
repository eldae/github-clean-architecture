package com.szypula.github.data;

import com.szypula.github.githubscreen.detail.domain.dao.UserDetailDAO;
import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel;
import com.szypula.github.githubscreen.list.domain.dao.UsersDAO;
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;
import com.szypula.github.data.mapper.UserMapper;
import com.szypula.github.network.RestAPI;
import com.szypula.github.network.model.User;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class InMemoryUsersDAO implements UsersDAO, UserDetailDAO {

    private RestAPI restAPI;
    private UserMapper userMapper;

    private List<User> users = new ArrayList<>();

    public InMemoryUsersDAO(RestAPI restAPI, UserMapper userMapper) {
        this.restAPI = restAPI;
        this.userMapper = userMapper;
    }

    @Override
    public Observable<List<UserDomainModel>> getAll() {
        if (users.isEmpty()) {
            return restAPI.getUsers()
                          .doOnNext(response -> users.addAll(response.getItems()))
                          .map(response -> userMapper.mapToUserDomainModels(response.getItems()));
        } else {
            return Observable.defer(() -> Observable.just(users))
                             .map(users -> userMapper.mapToUserDomainModels(users));
        }
    }

    @Override
    public Observable<UserDetailDomainModel> getUserDetail(String code) {
        return Observable.defer(() -> Observable.from(users))
                         .first(user -> String.valueOf(user.getId()).equals(code))
                         .map(user -> userMapper.mapToUserDetailDomainModel(user));
    }
}
