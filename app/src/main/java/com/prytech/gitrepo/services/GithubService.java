package com.prytech.gitrepo.services;

import com.prytech.gitrepo.models.GithubRepository;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {

    @GET("/users/{user}/repos")
    Call<ArrayList<GithubRepository>>userRepos(@Path("user") String user, @Query("page") String page);

}
