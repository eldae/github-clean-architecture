package com.szypula.github.network.model;

import java.util.List;

import lombok.Data;

@Data
public class GitHubResponse {

    private int total_count;
    private List<User> items;
}
