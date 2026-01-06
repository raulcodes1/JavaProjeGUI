package com.example.javaprojegui.model;

public class MalzemeAdmin {
    private int id;
    private String ad;
    private String kategori;
    private int miktar;
    private String birim;

    public MalzemeAdmin(int id, String ad, String kategori, int miktar, String birim) {
        this.id = id;
        this.ad = ad;
        this.kategori = kategori;
        this.miktar = miktar;
        this.birim = birim;
    }

    public int getId() { return id; }
    public String getAd() { return ad; }
    public String getKategori() { return kategori; }
    public int getMiktar() { return miktar; }
    public String getBirim() { return birim; }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public void setBirim(String birim) {
        this.birim = birim;
    }
}
