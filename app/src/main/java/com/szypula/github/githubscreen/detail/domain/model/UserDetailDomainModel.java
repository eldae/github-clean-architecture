package com.szypula.github.githubscreen.detail.domain.model;

import lombok.Data;
import lombok.experimental.Builder;

@Data @Builder
public class UserDetailDomainModel {

    private String code;
    private String name;
    private String logoUrl;
    private String site;
    private boolean isFavourite;
}
