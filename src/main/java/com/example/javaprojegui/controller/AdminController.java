package com.example.javaprojegui.controller;

import com.example.javaprojegui.model.*;
import com.example.javaprojegui.util.AdminCSVUtil;
import com.example.javaprojegui.util.DiyalogUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AdminController {

    @FXML
    private TableView<com.example.javaprojegui.model.Stok> stokTableView;

    @FXML
    private TableView<com.example.javaprojegui.model.MalzemeAdmin> MalzemlertableView;

    @FXML
    private TableView<com.example.javaprojegui.model.Kimyasallar> KimyasallartableView;

    @FXML
    private TableView<com.example.javaprojegui.model.KullaniciAdmin> KullaniciYonetimitableView;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Stok, Integer> HareketID;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Stok, String> UrunAdi;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Stok, String> Giris_Cikis;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Stok, Integer> S_Miktar;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Stok, LocalDate> Tarih;

    @FXML
    private TableColumn<com.example.javaprojegui.model.MalzemeAdmin, Integer> MalzemeID;

    @FXML
    private TableColumn<com.example.javaprojegui.model.MalzemeAdmin, String> MalzemeAdi;

    @FXML
    private TableColumn<com.example.javaprojegui.model.MalzemeAdmin, String> Kategori;

    @FXML
    private TableColumn<com.example.javaprojegui.model.MalzemeAdmin, Integer> Miktar;

    @FXML
    private TableColumn<com.example.javaprojegui.model.MalzemeAdmin, String> Birim;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Kimyasallar, Integer> KimyasalID;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Kimyasallar, String> KimyasalAdi;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Kimyasallar, String> TehlikeSinifi;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Kimyasallar, Integer> K_Miktar;

    @FXML
    private TableColumn<com.example.javaprojegui.model.Kimyasallar, LocalDate> SonKullanmaTarihi;

    @FXML
    private TableColumn<com.example.javaprojegui.model.KullaniciAdmin, Integer> KullaniciID;

    @FXML
    private TableColumn<com.example.javaprojegui.model.KullaniciAdmin, String> Ad_Soyad;

    @FXML
    private TableColumn<com.example.javaprojegui.model.KullaniciAdmin, String> Rol;

    @FXML
    private Button cikisBtn;

    @FXML
    private Button Kullanici_Ekle;

    @FXML
    private Button Kullanici_guncelle;

    @FXML
    private Button Kullanici_sil;
    @FXML
    private Button Malzeme_ekle;

    @FXML
    private Button Malzeme_guncelle;

    @FXML
    private Button Malzeme_sil;
    @FXML
    private Button StokHareket_Ekle;

    @FXML
    private Button StokHareket_guncelle;

    @FXML
    private Button StokHareket_sil;
    @FXML
    private Button Kimyasal_ekle;

    @FXML
    private Button Kimyasal_guncelle;

    @FXML
    private Button Kimyasal_sil;



    @FXML
    public void initialize() {

        stokTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        MalzemlertableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        KimyasallartableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        KullaniciYonetimitableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HareketID.setCellValueFactory(new PropertyValueFactory<>("id"));
        UrunAdi.setCellValueFactory(new PropertyValueFactory<>("urunAdi"));
        Giris_Cikis.setCellValueFactory(new PropertyValueFactory<>("girisCikis"));
        S_Miktar.setCellValueFactory(new PropertyValueFactory<>("miktar"));
        Tarih.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        MalzemeID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MalzemeAdi.setCellValueFactory(new PropertyValueFactory<>("ad"));
        Kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        Miktar.setCellValueFactory(new PropertyValueFactory<>("miktar"));
        Birim.setCellValueFactory(new PropertyValueFactory<>("birim"));
        KimyasalID.setCellValueFactory(new PropertyValueFactory<>("id"));
        KimyasalAdi.setCellValueFactory(new PropertyValueFactory<>("kimyasalAdi"));
        TehlikeSinifi.setCellValueFactory(new PropertyValueFactory<>("tehlikeSinifi"));
        K_Miktar.setCellValueFactory(new PropertyValueFactory<>("miktar"));
        SonKullanmaTarihi.setCellValueFactory(new PropertyValueFactory<>("sonKullanmaTarihi"));
        KullaniciID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Ad_Soyad.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        Rol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        stokTableView.getItems().setAll(AdminCSVUtil.stokOku("stokadmin.csv"));
        MalzemlertableView.getItems().setAll(AdminCSVUtil.malzemeOku("malzemeler.csv"));
        KimyasallartableView.getItems().setAll(AdminCSVUtil.kimyasalOku("kimyasallaradmin.csv"));
        KullaniciYonetimitableView.getItems().setAll(AdminCSVUtil.kullaniciOku("kullaniciadmin.csv"));
    }

    @FXML
    void stok_Ekle(ActionEvent event) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Stok Ekle");

        TextField urun = new TextField();
        urun.setPromptText("Ürün Adı");
        ComboBox<String> tip = new ComboBox<>();
        tip.getItems().addAll("GİRİŞ", "ÇIKIŞ");
        tip.setPromptText("Giriş / Çıkış");
        TextField miktar = new TextField();
        miktar.setPromptText("Miktar");

        DatePicker tarih = new DatePicker();
        tarih.setPromptText("Tarih");

        dialog.getDialogPane().setContent(new VBox(10, urun, tip, miktar, tarih));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                stokTableView.getItems().add(
                        new Stok(ID.randomId(), urun.getText(), tip.getValue(),
                                Integer.parseInt(miktar.getText()), tarih.getValue())
                );
            }
        });
        AdminCSVUtil.stokKaydet(
                stokTableView.getItems(),
                "stokadmin.csv"
        );
    }

    @FXML
    void stok_Guncelle(ActionEvent event) {

        var secili = stokTableView.getSelectionModel().getSelectedItem();
        if (secili == null) {
            DiyalogUtil.uyari("Uyarı", "Stok seçiniz");
            return;
        }

        TextField urun = new TextField(secili.getUrunAdi());
        ComboBox<String> tip = new ComboBox<>();
        tip.getItems().addAll("GİRİŞ", "ÇIKIŞ");
        tip.setValue(secili.getGirisCikis());
        TextField miktar = new TextField(String.valueOf(secili.getMiktar()));
        DatePicker tarih = new DatePicker(secili.getTarih());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Stok Güncelle");
        dialog.getDialogPane().setContent(new VBox(10, urun, tip, miktar, tarih));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                secili.setUrunAdi(urun.getText());
                secili.setGirisCikis(tip.getValue());
                secili.setMiktar(Integer.parseInt(miktar.getText()));
                secili.setTarih(tarih.getValue());
                stokTableView.refresh();
                AdminCSVUtil.stokKaydet(stokTableView.getItems(),"stokadmin.csv");
            }
        });
    }

    @FXML
    void stok_Sil(ActionEvent event) {

        var secili = stokTableView.getSelectionModel().getSelectedItem();
        if (secili == null) {
            DiyalogUtil.uyari("Uyarı", "Stok hareketi seçiniz");
            return;
        }
        if (DiyalogUtil.onayAl("Onay","Stok hareketi silinsin mi?")){
            stokTableView.getItems().remove(secili);
            AdminCSVUtil.stokKaydet(stokTableView.getItems(),"stokadmin.csv");}
    }

    @FXML
    void malzeme_Ekle(ActionEvent event) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Malzeme Ekle");

        TextField ad = new TextField();
        ad.setPromptText("Ürün adı");
        TextField kategori = new TextField();
        kategori.setPromptText("Kategori");
        TextField miktar = new TextField();
        miktar.setPromptText("Miktar");
        TextField birim = new TextField();
        birim.setPromptText("Birim");

        dialog.getDialogPane().setContent(new VBox(10, ad, kategori, miktar, birim));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                MalzemlertableView.getItems().add(
                        new MalzemeAdmin(ID.randomId(), ad.getText(),
                                kategori.getText(), Integer.parseInt(miktar.getText()), birim.getText())
                );
            }
        });
        AdminCSVUtil.malzemeKaydet(
                MalzemlertableView.getItems(),
                "malzemeler.csv"
        );
    }

    @FXML
    void malzeme_Guncelle(ActionEvent event) {

        var secili = MalzemlertableView.getSelectionModel().getSelectedItem();
        if (secili == null) {
            DiyalogUtil.uyari("Uyarı", "Malzeme seçiniz");
            return;
        }

        TextField ad = new TextField(secili.getAd());
        TextField kategori = new TextField(secili.getKategori());
        TextField miktar = new TextField(String.valueOf(secili.getMiktar()));
        TextField birim = new TextField(secili.getBirim());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Malzeme Güncelle");
        dialog.getDialogPane().setContent(new VBox(10, ad, kategori, miktar, birim));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                secili.setAd(ad.getText());
                secili.setKategori(kategori.getText());
                secili.setMiktar(Integer.parseInt(miktar.getText()));
                secili.setBirim(birim.getText());
                MalzemlertableView.refresh();
                AdminCSVUtil.malzemeKaydet(MalzemlertableView.getItems(), "malzemeler.csv");

            }
        });
    }

    @FXML
    void malzeme_Sil(ActionEvent event) {

        var secili = MalzemlertableView.getSelectionModel().getSelectedItem();
        if (secili == null) {
            DiyalogUtil.uyari("Uyarı", "Malzeme seçiniz");
            return;
        }
        if (DiyalogUtil.onayAl("Onay","Malzeme silinsin mi?")){
            MalzemlertableView.getItems().remove(secili);
            AdminCSVUtil.malzemeKaydet(MalzemlertableView.getItems(), "malzemeler.csv");
        }
    }


    @FXML
    void kimyasal_Ekle(ActionEvent event) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Kimyasal Ekle");

        TextField ad = new TextField();
        ad.setPromptText("Kimyasal adı");
        ComboBox<String> tehlike = new ComboBox<>();
        tehlike.getItems().addAll("Yanıcı", "Zehirli", "Aşındırıcı", "Patlayıcı");
        tehlike.setPromptText("Tehlike sınıfı");
        TextField miktar = new TextField();
        miktar.setPromptText("Miktar");
        DatePicker tarih = new DatePicker();
        tarih.setPromptText("Tarih");

        dialog.getDialogPane().setContent(new VBox(10, ad, tehlike, miktar, tarih));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                KimyasallartableView.getItems().add(
                        new Kimyasallar(ID.randomId(), ad.getText(),
                                tehlike.getValue(), Integer.parseInt(miktar.getText()), tarih.getValue())
                );
            }
        });
        AdminCSVUtil.kimyasalKaydet(
                KimyasallartableView.getItems(),
                "kimyasallaradmin.csv"
        );
    }

    @FXML
    void kimyasal_Guncelle(ActionEvent event) {

        var secili = KimyasallartableView.getSelectionModel().getSelectedItem();
        if (secili == null) {
            DiyalogUtil.uyari("Uyarı", "Kimyasal seçiniz");
            return;
        }

        TextField ad = new TextField(secili.getKimyasalAdi());
        ComboBox<String> tehlike = new ComboBox<>();
        tehlike.getItems().addAll("Yanıcı", "Zehirli", "Aşındırıcı", "Patlayıcı");
        tehlike.setValue(secili.getTehlikeSinifi());
        TextField miktar = new TextField(String.valueOf(secili.getMiktar()));
        DatePicker tarih = new DatePicker(secili.getSonKullanmaTarihi());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Kimyasal Güncelle");
        dialog.getDialogPane().setContent(new VBox(10, ad, tehlike, miktar, tarih));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                secili.setKimyasalAdi(ad.getText());
                secili.setTehlikeSinifi(tehlike.getValue());
                secili.setMiktar(Integer.parseInt(miktar.getText()));
                secili.setSonKullanmaTarihi(tarih.getValue());
                KimyasallartableView.refresh();
                AdminCSVUtil.kimyasalKaydet(KimyasallartableView.getItems(), "Kimyasallaradmin.csv");
            }
        });
    }

    @FXML
    void kimyasal_Sil(ActionEvent event) {

        var secili = KimyasallartableView.getSelectionModel().getSelectedItem();
        if (secili == null) {
            DiyalogUtil.uyari("Uyarı", "Kimyasal seçiniz");
            return;
        }
        if (DiyalogUtil.onayAl("Onay","Kimyasal silinsin mi?")){
            KimyasallartableView.getItems().remove(secili);
            AdminCSVUtil.kimyasalKaydet(KimyasallartableView.getItems(), "Kimyasallaradmin.csv");
        }
    }

    @FXML
    void kullanici_Ekle(ActionEvent event) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Kullanıcı Ekle");

        TextField adSoyad = new TextField();
        adSoyad.setPromptText("Ad ve soyad");
        ComboBox<String> rol = new ComboBox<>();
        rol.getItems().addAll("Admin", "Kullanıcı");
        rol.setPromptText("Rol");

        dialog.getDialogPane().setContent(new VBox(10, adSoyad, rol));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                KullaniciYonetimitableView.getItems().add(
                        new KullaniciAdmin(ID.randomId(), adSoyad.getText(), rol.getValue())
                );
            }
        });
        AdminCSVUtil.kullaniciKaydet(
                KullaniciYonetimitableView.getItems(),
                "kullaniciadmin.csv"
        );
    }

    @FXML
    void kullanici_Guncelle(ActionEvent event) {

        var secili = KullaniciYonetimitableView.getSelectionModel().getSelectedItem();
        if (secili == null) {
            DiyalogUtil.uyari("Uyarı", "Kullanıcı seçiniz");
            return;
        }

        TextField adSoyad = new TextField(secili.getAdSoyad());
        ComboBox<String> rol = new ComboBox<>();
        rol.getItems().addAll("Admin", "Kullanıcı");
        rol.setValue(secili.getRol());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Kullanıcı Güncelle");
        dialog.getDialogPane().setContent(new VBox(10, adSoyad, rol));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                secili.setAdSoyad(adSoyad.getText());
                secili.setRol(rol.getValue());
                KullaniciYonetimitableView.refresh();
                AdminCSVUtil.kullaniciKaydet(KullaniciYonetimitableView.getItems(), "kullaniciadmin.csv");

            }
        });
    }

    @FXML
    void kullanici_Sil(ActionEvent event) {

        var secili = KullaniciYonetimitableView.getSelectionModel().getSelectedItem();
        if (secili == null) {
            DiyalogUtil.uyari("Uyarı", "Kullanıcı seçiniz");
            return;
        }
        if (DiyalogUtil.onayAl("Onay","Kullanıcı silinsin mi?")){
            KullaniciYonetimitableView.getItems().remove(secili);
            AdminCSVUtil.kullaniciKaydet(KullaniciYonetimitableView.getItems(), "kullaniciadmin.csv");
        }
    }
    @FXML
    void cikisYap(ActionEvent event){
        if(!DiyalogUtil.onayAl("Çıkış","Çıkış yapılsın mı?")){
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojegui/view/Login.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource())
                    .getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Giriş Yap");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}