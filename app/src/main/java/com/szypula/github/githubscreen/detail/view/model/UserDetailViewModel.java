package com.szypula.github.githubscreen.detail.view.model;

import lombok.Data;
import lombok.experimental.Builder;

@Data @Builder
public class UserDetailViewModel {

    private String name;
    private String logoUrl;
    private String site;
    private String phone;
    private boolean isFavourite;
}
