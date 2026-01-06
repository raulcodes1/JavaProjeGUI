package com.example.javaprojegui.util;

import com.example.javaprojegui.model.Malzeme;
import com.example.javaprojegui.model.SonIslem;
import com.example.javaprojegui.model.SepetItem;
import com.example.javaprojegui.model.Deney;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
//import java.time.LocalDate;
import java.io.PrintWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserCSVUtil {

    private static final String MALZEME_PATH =
            "src/main/java/com/example/javaprojegui/database/malzemeler.csv";

    public static List<Malzeme> readMalzemeler() {

        List<Malzeme> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(MALZEME_PATH))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    firstLine = false; // header atla
                    continue;
                }

                String[] d = line.split(",");

                list.add(new Malzeme(
                        d[1],                    // ad
                        d[2],                    // tür
                        Integer.parseInt(d[3]),  // stok
                        d[6],                    // durum
                        d[5]                     // son güncellenme
                ));
            }

        } catch (Exception e) {
            System.out.println("❌ malzemeler.csv okunamadı!");
            e.printStackTrace();
        }

        return list;
    }

    public static void writeAll(List<Malzeme> list) {

        try (FileWriter fw = new FileWriter(MALZEME_PATH)) {

            fw.write("id,ad,tur,stok,birim,skt,durum\n");

            int id = 1;
            for (Malzeme m : list) {
                fw.write(
                        id++ + "," +
                                m.getAd() + "," +
                                m.getTur() + "," +
                                m.getStok() + "," +
                                "Adet," +
                                m.getSonGuncellenme() + "," +
                                m.getDurum() + "\n"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==== SON İŞLEMLER CSV ====
    private static final String SON_ISLEM_PATH =
            "src/main/java/com/example/javaprojegui/database/islemler.csv";


    public static void logIslem(String islem, String malzeme) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String tarihSaat = LocalDateTime.now().format(formatter);

        try (PrintWriter pw = new PrintWriter(
                new FileWriter(SON_ISLEM_PATH, true))) {

            pw.println(
                    tarihSaat + "," +
                            islem + "," +
                            malzeme
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<SonIslem> readSonIslemler() {

        List<SonIslem> list = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(SON_ISLEM_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");

                if (d.length < 3) continue;

                list.add(new SonIslem(d[0], d[1], d[2]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==== SEPET CSV ====
    private static final String SEPET_PATH =
            "src/main/java/com/example/javaprojegui/database/sepet.csv";

    // SEPET OKU
    public static List<SepetItem> readSepet() {

        List<SepetItem> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(SEPET_PATH))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] d = line.split(",");

                Malzeme m = new Malzeme(
                        d[0],                  // ad
                        d[1],                  // kategori
                        Integer.parseInt(d[3]),// stok
                        d[4],                  // durum
                        ""                     // tarih (önemsiz)
                );

                int miktar = Integer.parseInt(d[2]);

                // DOĞRU CONSTRUCTOR
                list.add(new SepetItem(m, miktar));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // SEPET YAZ
    public static void writeSepet(List<SepetItem> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SEPET_PATH))) {
            pw.println("ad,kategori,miktar,stok,durum");
            for (SepetItem s : list) {
                pw.println(
                        s.getAd() + "," +
                                s.getKategori() + "," +
                                s.getMiktar() + "," +
                                s.getStok() + "," +
                                s.getDurum()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getSepetUrunSayisi() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("sepet.csv"))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { // header atla
                    first = false;
                    continue;
                }
                if (!line.trim().isEmpty()) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Sepet okunamadı: " + e.getMessage());
        }
        return count;
    }

    public static void writeDeney(
            String id,
            String tarih,
            int toplamMalzeme,
            int toplamMiktar,
            String aciklama
    ) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(DENEY_PATH, true))) {

            pw.println(
                    id + "," +
                            tarih + "," +
                            toplamMalzeme + "," +
                            toplamMiktar + "," +
                            aciklama
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static final String DENEY_PATH =
            "src/main/java/com/example/javaprojegui/database/deneyler.csv";

    public static List<Deney> readDeneyler() {

        List<Deney> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(DENEY_PATH))) {

            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {

                if (first) {
                    first = false;
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");

                if (d.length < 5) continue;

                list.add(new Deney(
                        d[0],                       // deneyId
                        d[1],                       // tarih
                        Integer.parseInt(d[2]),     // toplamMalzeme
                        Integer.parseInt(d[3]),     // toplamMiktar
                        d[4]                        // aciklama
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    private static final String DENEY_DETAY_PATH =
            "src/main/java/com/example/javaprojegui/database/deney_detay.csv";

    public static void writeDeneyDetay(
            String deneyId,
            String malzeme,
            String kategori,
            int miktar
    ) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(DENEY_DETAY_PATH, true))) {

            pw.println(
                    deneyId + "," +
                            malzeme + "," +
                            kategori + "," +
                            miktar
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteDeney(String deneyId) {

        // deneyler.csv
        deleteFromCsv(DENEY_PATH, 0, deneyId);

        // deney_detay.csv
        deleteFromCsv(DENEY_DETAY_PATH, 0, deneyId);
    }

    private static void deleteFromCsv(
            String path, int index, String value) {

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(value + ",")) {
                    lines.add(line);
                }
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
                for (String l : lines) pw.println(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readDeneyDetay(String deneyId) {

        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(DENEY_DETAY_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");

                if (d.length < 4) continue;

                // d[0] = deneyId
                if (d[0].equals(deneyId)) {
                    list.add(
                            "Malzeme: " + d[1] +
                                    " | Kategori: " + d[2] +
                                    " | Miktar: " + d[3]
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}