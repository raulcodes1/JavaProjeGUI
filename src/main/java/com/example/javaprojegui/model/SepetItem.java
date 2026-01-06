package com.example.javaprojegui.model;

public class SepetItem {

    private Malzeme malzeme;
    private int miktar;

    public SepetItem(Malzeme malzeme, int miktar) {
        this.malzeme = malzeme;
        this.miktar = miktar;
    }

    // ===== GETTERLAR =====
    public String getAd() {
        return malzeme.getAd();
    }

    public String getKategori() {
        return malzeme.getTur();
    }

    public int getMiktar() {
        return miktar;
    }

    public int getStok() {
        return malzeme.getStok();
    }

    public String getDurum() {
        return "YETERLI";
    }

    public Malzeme getMalzeme() {
        return malzeme;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }
}
