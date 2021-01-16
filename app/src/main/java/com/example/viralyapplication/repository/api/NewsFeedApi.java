package com.example.viralyapplication.repository.api;

import com.example.viralyapplication.repository.model.CreatePostModel;
import com.example.viralyapplication.repository.model.RegisterAccountModel;
import com.example.viralyapplication.repository.model.getUserModel;
import com.example.viralyapplication.repository.model.newsfeed.NewsFeedModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NewsFeedApi {
    @Headers("Content-Type: application/json")
    @GET("users/{uid}")
    Call<getUserModel> getUser(@Path("uid") String uid);

    @Headers("Content-Type: application/json")
    @GET("users/feed")
    Call<NewsFeedModel> getNewsFeed();


    @Headers("Content-Type: application/json")
    @POST("post/upload")
    Call<CreatePostModel> uploadFile(@Part MultipartBody.Part image);

}
