package com.szypula.github.githubscreen.list.domain.usecase;

import com.szypula.github.githubscreen.list.domain.dao.UsersDAO;
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;
import com.szypula.github.core.usecase.AbstractUseCase;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class GetAllUsersUseCase extends AbstractUseCase<List<UserDomainModel>> {

    private UsersDAO usersDAO;

    public GetAllUsersUseCase(Scheduler executionScheduler,
                              UsersDAO usersDAO) {
        super(executionScheduler);
        this.usersDAO = usersDAO;
    }

    @Override
    protected Observable<List<UserDomainModel>> emit() {
        return usersDAO.getAll()
                       .map(list -> {
                              Collections.sort(list);
                              return list;
                          });
    }
}
