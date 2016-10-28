package com.szypula.github.githubscreen.list.view.model;

import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;

import java.util.ArrayList;
import java.util.List;

public class UserViewModelMapper {

    public List<UserViewModel> map(List<UserDomainModel> domainUsers) {
        List<UserViewModel> viewUsers = new ArrayList<>(domainUsers.size());
        for (UserDomainModel domainUser : domainUsers) {
            viewUsers.add(UserViewModel.builder()
                                          .name(domainUser.getName())
                                          .logoUrl(domainUser.getLogoURL())
                                          .code(domainUser.getCode())
                                          .build());
        }

        return viewUsers;
    }
}
