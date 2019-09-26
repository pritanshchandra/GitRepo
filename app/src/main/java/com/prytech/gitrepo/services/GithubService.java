package com.prytech.gitrepo.services;

import com.prytech.gitrepo.models.GithubRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @GET("/users/{user}/repos")
    Call<List<GithubRepository>>userRepos(@Path("user") String user);

}
