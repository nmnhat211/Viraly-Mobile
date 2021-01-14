package com.example.viralyapplication.repository.api;

import com.example.viralyapplication.repository.model.getUserModel;
import com.example.viralyapplication.repository.model.newsfeed.NewsFeedModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface NewsFeedApi {
    @Headers("Content-Type: application/json")
    @GET("users/{uid}")
    Call<getUserModel> getUser(@Path("uid") String uid);

    @Headers("Content-Type: application/json")
    @GET("users/feed")
    Call<NewsFeedModel> getNewsFeed();


}
