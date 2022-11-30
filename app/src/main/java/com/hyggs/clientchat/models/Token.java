package com.hyggs.clientchat.models;

public class Token {
    private String id;
    private String date;
    private String date_expire;
    private String token;
    private String id_user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_expire() {
        return date_expire;
    }

    public void setDate_expire(String date_expire) {
        this.date_expire = date_expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
