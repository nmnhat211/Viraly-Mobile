package com.example.viralyapplication.utility;

public class ErrorMessageModel {
    private int statusCode;
    private String message;

    public ErrorMessageModel() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
