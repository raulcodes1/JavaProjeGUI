package com.example.javaprojegui.model;
import java.time.LocalDate;

public class Kimyasallar {
    private int id;
    private String kimyasalAdi;
    private String tehlikeSinifi;
    private int miktar;
    private LocalDate sonKullanmaTarihi;

    public Kimyasallar(int id, String kimyasalAdi, String tehlikeSinifi,
                       int miktar, LocalDate sonKullanmaTarihi) {
        this.id = id;
        this.kimyasalAdi = kimyasalAdi;
        this.tehlikeSinifi = tehlikeSinifi;
        this.miktar = miktar;
        this.sonKullanmaTarihi = sonKullanmaTarihi;
    }

    public int getId() {
        return id;
    }

    public String getKimyasalAdi() {
        return kimyasalAdi;
    }

    public String getTehlikeSinifi() {
        return tehlikeSinifi;
    }

    public int getMiktar() {
        return miktar;
    }

    public LocalDate getSonKullanmaTarihi() {
        return sonKullanmaTarihi;
    }

    public void setKimyasalAdi(String kimyasalAdi) {
        this.kimyasalAdi = kimyasalAdi;
    }

    public void setSonKullanmaTarihi(LocalDate sonKullanmaTarihi) {
        this.sonKullanmaTarihi = sonKullanmaTarihi;
    }

    public void setTehlikeSinifi(String tehlikeSinifi) {
        this.tehlikeSinifi = tehlikeSinifi;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }
}
