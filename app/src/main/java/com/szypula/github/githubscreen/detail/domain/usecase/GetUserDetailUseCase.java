package com.szypula.github.githubscreen.detail.domain.usecase;

import com.szypula.github.githubscreen.detail.domain.dao.UserDetailDAO;
import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel;
import com.szypula.github.core.usecase.AbstractUseCase;

import rx.Observable;
import rx.Scheduler;

public class GetUserDetailUseCase extends AbstractUseCase<UserDetailDomainModel> {

    private UserDetailDAO userDetailDAO;
    private String code;

    public GetUserDetailUseCase(Scheduler executionScheduler,
                                UserDetailDAO userDetailDAO,
                                String code) {
        super(executionScheduler);
        this.userDetailDAO = userDetailDAO;
        this.code = code;
    }

    @Override
    protected Observable<UserDetailDomainModel> emit() {
        return userDetailDAO.getUserDetail(code);
    }
}
