package com.hyggs.clientchat.models;

public class Chats {
    //A modificar
    String title;
    String content;
    String type;
    String status;
    String lasttime;

    public Chats(String title,String content,String type, String status,String lasttime) {
        this.title = title;
        this.status = status;
        this.content=content;
        this.type=type;
        this.lasttime=lasttime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
