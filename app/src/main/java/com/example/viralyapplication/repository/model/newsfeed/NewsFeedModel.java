package com.example.viralyapplication.repository.model.newsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewsFeedModel implements Serializable {

    @SerializedName("posts")
    @Expose
    private List<PostModel> posts = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
