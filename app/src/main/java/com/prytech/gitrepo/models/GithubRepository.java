package com.prytech.gitrepo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GithubRepository {

    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
}
