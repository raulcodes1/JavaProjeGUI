package com.example.javaprojegui.model;

public class Deney {

    private String deneyId;
    private String tarih;
    private int toplamMalzeme;
    private int toplamMiktar;
    private String aciklama;

    public Deney(String deneyId, String tarih, int toplamMalzeme, int toplamMiktar, String aciklama) {
        this.deneyId = deneyId;
        this.tarih = tarih;
        this.toplamMalzeme = toplamMalzeme;
        this.toplamMiktar = toplamMiktar;
        this.aciklama = aciklama;
    }

    public String getDeneyId() { return deneyId; }
    public String getTarih() { return tarih; }
    public int getToplamMalzeme() { return toplamMalzeme; }
    public int getToplamMiktar() { return toplamMiktar; }
    public String getAciklama() { return aciklama; }
}
