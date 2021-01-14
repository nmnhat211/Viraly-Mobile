package com.example.viralyapplication.repository.model.newsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostModel implements Serializable {
    @SerializedName("content")
    @Expose
    private List<ContentModel> content = null;
    @SerializedName("likes")
    @Expose
    private List<Object> likes = null;
    @SerializedName("comments")
    @Expose
    private List<Object> comments = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("user")
    @Expose
    private UserModel user;

    public  List<ContentModel> getContent() {
        return content;
    }

    public void setContent(List<ContentModel> content) {
        this.content = content;
    }

    public List<Object> getLikes() {
        return likes;
    }

    public void setLikes(List<Object> likes) {
        this.likes = likes;
    }

    public List<Object> getComments() {
        return comments;
    }

    public void setComments(List<Object> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
