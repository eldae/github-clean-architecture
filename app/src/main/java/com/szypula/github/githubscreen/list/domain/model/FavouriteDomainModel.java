package com.szypula.github.githubscreen.list.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavouriteDomainModel {

    private String code;
    private boolean isFavourite;
}
