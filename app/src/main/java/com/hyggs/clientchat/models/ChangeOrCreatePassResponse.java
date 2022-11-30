package com.hyggs.clientchat.models;

public class ChangeOrCreatePassResponse {

    private String mensaje;
    private String status;
    private DataReceivedPassword datos_recibidos;

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

    public DataReceivedPassword getDatos_recibidos() {
        return datos_recibidos;
    }

    public void setDatos_recibidos(DataReceivedPassword datos_recibidos) {
        this.datos_recibidos = datos_recibidos;
    }
}
