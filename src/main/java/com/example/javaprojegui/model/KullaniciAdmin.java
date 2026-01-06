package com.example.javaprojegui.model;

public class KullaniciAdmin {

    private int id;
    private String adSoyad;
    private String rol;

    public KullaniciAdmin(int id, String adSoyad, String rol) {
        this.id = id;
        this.adSoyad = adSoyad;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public String getRol() {
        return rol;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

