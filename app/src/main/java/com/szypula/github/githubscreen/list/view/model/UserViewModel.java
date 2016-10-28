package com.szypula.github.githubscreen.list.view.model;

import lombok.Data;
import lombok.experimental.Builder;

@Data @Builder
public class UserViewModel {

    private String name;
    private String logoUrl;
    private String code;
}
