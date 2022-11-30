package com.hyggs.clientchat.models;

public class Chating {
    //A modificar
    String title;
    String content;
    String status;
    int type;


    public Chating(String title,String content,int type, String status) {
        this.title = title;
        this.status = status;
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
