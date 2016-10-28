package com.szypula.github.githubscreen.list.domain.usecase;

import com.szypula.github.githubscreen.list.domain.dao.UsersDAO;
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;
import com.szypula.github.core.usecase.AbstractUseCase;

import java.util.List;

import rx.Observable;
import rx.Scheduler;

public class GetFavouriteUsersUseCase extends AbstractUseCase<List<UserDomainModel>> {

    private UsersDAO usersDAO;

    public GetFavouriteUsersUseCase(Scheduler executionScheduler,
                                    UsersDAO usersDAO) {
        super(executionScheduler);
        this.usersDAO = usersDAO;
    }

    @Override
    protected Observable<List<UserDomainModel>> emit() {
        return usersDAO.getAll()
                       .flatMap(Observable::from)
                       .filter(UserDomainModel::isFavourite)
                       .toSortedList();
    }
}
