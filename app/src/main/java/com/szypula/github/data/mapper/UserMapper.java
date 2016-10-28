package com.szypula.github.data.mapper;

import com.szypula.github.githubscreen.detail.domain.model.UserDetailDomainModel;
import com.szypula.github.githubscreen.list.domain.model.UserDomainModel;
import com.szypula.github.data.PreferencesFavouritesDAO;
import com.szypula.github.network.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserMapper {

    private PreferencesFavouritesDAO preferencesFavouritesDAO;

    public UserMapper(PreferencesFavouritesDAO preferencesFavouritesDAO) {
        this.preferencesFavouritesDAO = preferencesFavouritesDAO;
    }

    public List<UserDomainModel> mapToUserDomainModels(List<User> users) {
        Set<String> favourites = preferencesFavouritesDAO.getFavourites();
        List<UserDomainModel> domainModels = new ArrayList<>(users.size());
        for (User user : users) {
            domainModels.add(UserDomainModel.builder()
                                            .name(user.getLogin())
                                            .logoURL(user.getAvatar_url())
                                            .code(String.valueOf(user.getId()))
                                            .isFavourite(favourites.contains(String.valueOf(user.getId())))
                                            .build());
        }
        return domainModels;
    }

    public UserDetailDomainModel mapToUserDetailDomainModel(User user) {
        boolean isFavourite = preferencesFavouritesDAO.getFavourites().contains(String.valueOf(user.getId()));
        return UserDetailDomainModel.builder()
                                    .code(String.valueOf(user.getId()))
                                    .name(user.getLogin())
                                    .logoUrl(user.getAvatar_url())
                                    .site(user.getHtml_url())
                                    .isFavourite(isFavourite)
                                    .build();
    }

}
