package com.szypula.github.network;


import com.szypula.github.network.model.GitHubResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface RestAPI {

    @GET("/search/users?q=followers:>10000")
    Observable<GitHubResponse> getUsers();
}
