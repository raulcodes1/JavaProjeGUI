package com.example.javaprojegui.util;

import com.example.javaprojegui.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminCSVUtil {

    private static final String DATABASE_PATH =
            "src/main/java/com/example/javaprojegui/database/";

    /* ================= MALZEME ================= */

    public static void malzemeKaydet(List<MalzemeAdmin> liste, String dosyaAdi) {
        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(DATABASE_PATH + dosyaAdi))) {

            pw.println("id,ad,kategori,miktar,birim");
            for (MalzemeAdmin m : liste) {
                pw.println(
                        m.getId() + "," +
                                m.getAd() + "," +
                                m.getKategori() + "," +
                                m.getMiktar() + "," +
                                m.getBirim()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<MalzemeAdmin> malzemeOku(String dosyaAdi) {
        List<MalzemeAdmin> liste = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(DATABASE_PATH + dosyaAdi))) {

            br.readLine(); // header
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split(",");
                liste.add(new MalzemeAdmin(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2],
                        Integer.parseInt(d[3]),
                        d[4]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /* ================= KİMYASAL ================= */

    public static void kimyasalKaydet(List<Kimyasallar> liste, String dosyaAdi) {
        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(DATABASE_PATH + dosyaAdi))) {

            pw.println("id,ad,tehlike,miktar,sonKullanma");
            for (Kimyasallar k : liste) {
                pw.println(
                        k.getId() + "," +
                                k.getKimyasalAdi() + "," +
                                k.getTehlikeSinifi() + "," +
                                k.getMiktar() + "," +
                                k.getSonKullanmaTarihi()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Kimyasallar> kimyasalOku(String dosyaAdi) {
        List<Kimyasallar> liste = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(DATABASE_PATH + dosyaAdi))) {

            br.readLine();
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split(",");
                liste.add(new Kimyasallar(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2],
                        Integer.parseInt(d[3]),
                        LocalDate.parse(d[4])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /* ================= KULLANICI ================= */

    public static void kullaniciKaydet(List<KullaniciAdmin> liste, String dosyaAdi) {
        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(DATABASE_PATH + dosyaAdi))) {

            pw.println("id,adSoyad,rol");
            for (KullaniciAdmin k : liste) {
                pw.println(
                        k.getId() + "," +
                                k.getAdSoyad() + "," +
                                k.getRol()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<KullaniciAdmin> kullaniciOku(String dosyaAdi) {
        List<KullaniciAdmin> liste = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(DATABASE_PATH + dosyaAdi))) {

            br.readLine();
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split(",");
                liste.add(new KullaniciAdmin(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /* ================= STOK ================= */

    public static void stokKaydet(List<Stok> liste, String dosyaAdi) {
        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(DATABASE_PATH + dosyaAdi))) {

            pw.println("id,urunAdi,girisCikis,miktar,tarih");
            for (Stok s : liste) {
                pw.println(
                        s.getId() + "," +
                                s.getUrunAdi() + "," +
                                s.getGirisCikis() + "," +
                                s.getMiktar() + "," +
                                s.getTarih()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Stok> stokOku(String dosyaAdi) {
        List<Stok> liste = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(DATABASE_PATH + dosyaAdi))) {

            br.readLine();
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split(",");
                liste.add(new Stok(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2],
                        Integer.parseInt(d[3]),
                        LocalDate.parse(d[4])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
