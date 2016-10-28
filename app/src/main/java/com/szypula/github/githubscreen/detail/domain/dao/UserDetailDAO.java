package com.szypula.github.githubscreen.detail.domain.dao;

import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel;

import rx.Observable;

public interface UserDetailDAO {

    Observable<UserDetailDomainModel> getUserDetail(String code);
}
