package com.szypula.github.githubscreen.list.domain.model;

import lombok.Data;
import lombok.experimental.Builder;

@Data @Builder
public class UserDomainModel implements Comparable<UserDomainModel> {

    private String name;
    private String logoURL;
    private String code;
    private boolean isFavourite;

    @Override
    public int compareTo(UserDomainModel userDomainModel) {
        return name.compareTo(userDomainModel.name);
    }
}
