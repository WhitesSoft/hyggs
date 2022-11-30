package com.hyggs.clientchat.models;

public class LoginResponse {

    private String mensaje;
    private String status;
    private UserData datos;
    private UserAddData add_data;

    public UserAddData getAdd_data() {
        return add_data;
    }

    public void setAdd_data(UserAddData add_data) {
        this.add_data = add_data;
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
