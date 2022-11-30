package com.hyggs.clientchat.models;

import android.graphics.drawable.Drawable;

public class Language {
    int state;
    String title;
    int imgcountry;
    public  Language(){}

    public  Language(int state, String title, int imgcountry){
        this.state=state;
        this.title=title;
        this.imgcountry=imgcountry;
    }

    public int getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public int getImgcountry() {
        return imgcountry;
    }

    public void setImgcountry(int imgcountry) {
        this.imgcountry = imgcountry;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setState(int state) {
        this.state = state;
    }

}
