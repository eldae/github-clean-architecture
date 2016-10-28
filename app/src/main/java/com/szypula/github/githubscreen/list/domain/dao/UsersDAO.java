package com.szypula.github.githubscreen.list.domain.dao;

import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;

import java.util.List;

import rx.Observable;

public interface UsersDAO {

    Observable<List<UserDomainModel>> getAll();
}
