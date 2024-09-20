package com.example.facultades.enums;

public enum Socket {
    BROKER("/tema"),
    ADMIN_PREFIJO("/tema/admin/notificacion");

    private final String ruta;

    Socket(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
