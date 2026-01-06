package com.example.javaprojegui.model;

public class Malzeme {

    private String ad;
    private String tur;
    private int stok;
    private String durum;
    private String sonGuncellenme;

    public Malzeme(String ad, String tur, int stok, String durum, String sonGuncellenme) {
        this.ad = ad;
        this.tur = tur;
        this.stok = stok;
        this.durum = durum;
        this.sonGuncellenme = sonGuncellenme;
    }

    public String getAd() {
        return ad;
    }
    public String getTur() {
        return tur;
    }
    public int getStok() {
        return stok;
    }
    public String getDurum() {
        return durum;
    }
    public String getSonGuncellenme() {
        return sonGuncellenme;
    }

    public void setStok(int stok) {
        this.stok = stok;

        if (stok <= 5) {
            this.durum = "Kritik";
        } else {
            this.durum = "Aktif";
        }
    }

}
