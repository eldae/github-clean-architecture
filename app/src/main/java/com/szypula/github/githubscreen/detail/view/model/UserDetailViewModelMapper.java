package com.szypula.github.githubscreen.detail.view.model;

import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel;

public class UserDetailViewModelMapper {

    public UserDetailViewModel map(UserDetailDomainModel domainUser) {
        return UserDetailViewModel.builder()
                                  .name(domainUser.getName())
                                  .logoUrl(domainUser.getLogoUrl())
                                  .site(domainUser.getSite())
                                  .isFavourite(domainUser.isFavourite())
                                  .build();
    }
}
