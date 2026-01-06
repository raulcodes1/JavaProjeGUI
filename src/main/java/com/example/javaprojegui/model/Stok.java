package com.example.javaprojegui.model;

import java.time.LocalDate;

public class Stok {

    private int id;
    private String urunAdi;
    private String girisCikis;
    private int miktar;
    private LocalDate tarih;

    public Stok(int id, String urunAdi, String girisCikis, int miktar, LocalDate tarih) {
        this.id = id;
        this.urunAdi = urunAdi;
        this.girisCikis = girisCikis;
        this.miktar = miktar;
        this.tarih = tarih;
    }

    // GETTERLAR
    public int getId() { return id; }
    public String getUrunAdi() { return urunAdi; }
    public String getGirisCikis() { return girisCikis; }
    public int getMiktar() { return miktar; }
    public LocalDate getTarih() { return tarih; }

    // SETTERLAR
    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public void setGirisCikis(String girisCikis) {
        this.girisCikis = girisCikis;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }
}
