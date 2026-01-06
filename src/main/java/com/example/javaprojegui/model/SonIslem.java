package com.example.javaprojegui.model;

public class SonIslem {

    private String tarih;
    private String islem;
    private String malzeme;

    public SonIslem(String tarih, String islem, String malzeme) {
        this.tarih = tarih;
        this.islem = islem;
        this.malzeme = malzeme;
    }

    public String getTarih() {
        return tarih;
    }

    public String getIslem() {
        return islem;
    }

    public String getMalzeme() {
        return malzeme;
    }
}
