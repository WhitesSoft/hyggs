package com.hyggs.clientchat.models;

public class RegisterResponse {

    private String mensaje;
    private String status;
    private UserData datos;
    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserData getDatos() {
        return datos;
    }

    public void setDatos(UserData datos) {
        this.datos = datos;
    }

}
