package com.szypula.github.network.model;

import lombok.Data;

@Data
public class User {

    private long id;
    private String login;
    private String avatar_url;
    private String html_url;
}
