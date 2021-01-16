package com.example.viralyapplication.repository.model;

public class FooterModel {
    private String gmail;
    private String nameApp;
    private String Image;


    public FooterModel(String gmail, String nameApp, String image) {
        this.gmail = gmail;
        this.nameApp = nameApp;
        Image = image;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getNameApp() {
        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
