package com.example.viralyapplication.repository.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentsModel implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("comment")
    @Expose
    private String comment;

    public CommentsModel(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public CommentsModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
