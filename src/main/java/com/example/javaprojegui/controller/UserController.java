package com.example.javaprojegui.controller;

import com.example.javaprojegui.model.Malzeme;
import com.example.javaprojegui.util.UserCSVUtil;
import com.example.javaprojegui.model.SonIslem;
import com.example.javaprojegui.model.SepetItem;
import com.example.javaprojegui.model.Session;
import com.example.javaprojegui.model.Deney;
import com.example.javaprojegui.util.AuthCSVUtil;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;

import java.io.FileReader;
import java.time.LocalDate;

import javafx.scene.chart.PieChart;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class UserController {

    // ===== ANA SAYFA =====
    @FXML private Label lblToplam;
    @FXML private Label lblKritik;
    @FXML private Label lblTur;
    @FXML private Label lblTarih;
    @FXML private Label lblSepet;
    @FXML
    private PieChart kategoriPieChart;

    @FXML private TableView<SonIslem> sonIslemlerTable;
    @FXML private TableColumn<SonIslem, String> colTarih;
    @FXML private TableColumn<SonIslem, String> colIslem;
    @FXML private TableColumn<SonIslem, String> colMalzeme;


    @FXML private ListView<String> uyariListView;


    // ===== TAB =====
    @FXML private Tab tabMalzemeler;


    // ===== FİLTRE =====
    @FXML private TextField txtAra;
    @FXML private CheckBox chkKritik;


    // ===== MALZEMELER =====
    @FXML private TableView<Malzeme> tblMalzemeler;
    @FXML private TableColumn<Malzeme, String> colAd;
    @FXML private TableColumn<Malzeme, String> colTur;
    @FXML private TableColumn<Malzeme, Integer> colStok;
    @FXML private TableColumn<Malzeme, String> colDurum;
    @FXML private TableColumn<Malzeme, String> colSonGuncellenme;
    private ObservableList<Malzeme> masterList;
    private FilteredList<Malzeme> filteredList;


    // ===== DENEYLER =====
    @FXML private TableView<Deney> tblDeneyler;
    @FXML private TableColumn<Deney, String> colDeneyId;
    @FXML private TableColumn<Deney, String> colDeneyTarih;
    @FXML private TableColumn<Deney, Integer> colDeneyToplam;
    @FXML private TableColumn<Deney, Integer> colDeneyMiktar;
    @FXML private TableColumn<Deney, String> colDeneyAciklama;

    @FXML
    private TextField txtDeneyAra;

    private ObservableList<Deney> deneyMasterList;
    private FilteredList<Deney> deneyFilteredList;



    // ===== PROFIL =====
    @FXML private TextField txtProfilUsername;
    @FXML private TextField txtProfilEmail;
    @FXML private TextField txtProfilRole;

    @FXML private PasswordField txtEskiSifre;
    @FXML private PasswordField txtYeniSifre;
    @FXML private PasswordField txtYeniSifreTekrar;


    @FXML
    public void initialize() {

        // ==== KOLON BAĞLANTILARI ====
        tblMalzemeler.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        sonIslemlerTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        tblDeneyler.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );


        colAd.setCellValueFactory(new PropertyValueFactory<>("ad"));
        colTur.setCellValueFactory(new PropertyValueFactory<>("tur"));
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colDurum.setCellValueFactory(new PropertyValueFactory<>("durum"));
        colSonGuncellenme.setCellValueFactory(
                new PropertyValueFactory<>("sonGuncellenme")
        );

        colTarih.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        colIslem.setCellValueFactory(new PropertyValueFactory<>("islem"));
        colMalzeme.setCellValueFactory(new PropertyValueFactory<>("malzeme"));

        loadSonIslemler();


        // ==== DURUM RENKLENDİRME ====
        colDurum.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);

                    if (item.equalsIgnoreCase("KRITIK")) {
                        setStyle("-fx-text-fill:red;-fx-font-weight:bold;");
                    } else if (item.equalsIgnoreCase("AKTIF")) {
                        setStyle("-fx-text-fill:green;-fx-font-weight:bold;");
                    } else {
                        setStyle("-fx-text-fill:black;");
                    }
                }
            }
        });

        // ==== KRİTİK STOK SATIR RENKLENDİRME ====
        tblMalzemeler.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Malzeme malzeme, boolean empty) {
                super.updateItem(malzeme, empty);

                if (malzeme == null || empty) {
                    setStyle("");
                } else if (malzeme.getStok() <= 2) {
                    setStyle("-fx-background-color:#ffe0e0;");
                } else {
                    setStyle("");
                }
            }
        });

        // ==== OTOMATİK FİLTRE ====
        txtAra.textProperty().addListener((obs, o, n) -> applyAutoFilter());
        chkKritik.selectedProperty().addListener((obs, o, n) -> applyAutoFilter());

        // ==== TAB SEÇİLİNCE CSV OKU ====
        tabMalzemeler.setOnSelectionChanged(event -> {
            if (tabMalzemeler.isSelected()) {
                loadMalzemelerFromCSV();
            }
        });

        colSonGuncellenme.setCellValueFactory(
                new PropertyValueFactory<>("sonGuncellenme")
        );

        // ==== ANA SAYFA TARİH ====
        lblTarih.setText(LocalDate.now().toString());

        // ==== ANA SAYFA VERİLERİ ====
        loadDashboardData();

        //kategoriGrafikYukle();

        loadKategoriGrafik();

        tblSepet.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        // ==== SEPET TABLO BAĞLANTILARI ====
        colSepetAd.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getMalzeme().getAd()
                )
        );

        colSepetKategori.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getMalzeme().getTur()
                )
        );

        colSepetStok.setCellValueFactory(
                cell -> new SimpleIntegerProperty(
                        cell.getValue().getMalzeme().getStok()
                ).asObject()
        );

        colSepetDurum.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getDurum()
                )
        );

        colSepetMiktar.setCellValueFactory(
                new PropertyValueFactory<>("miktar")
        );


        tblSepet.setItems(sepetList);

        filteredSepet = new FilteredList<>(sepetList, p -> true);
        tblSepet.setItems(filteredSepet);

        txtSepetAra.textProperty().addListener((obs, o, n) -> applySepetFilter());
        chkYetersiz.selectedProperty().addListener((obs, o, n) -> applySepetFilter());

        loadSepet();
        loadSepetOzet();

        txtProfilUsername.setText(Session.currentUser);
        txtProfilEmail.setText(Session.currentEmail);
        txtProfilRole.setText(Session.currentRole);


        //    DENEYLER

        colDeneyId.setCellValueFactory(new PropertyValueFactory<>("deneyId"));
        colDeneyTarih.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        colDeneyToplam.setCellValueFactory(new PropertyValueFactory<>("toplamMalzeme"));
        colDeneyMiktar.setCellValueFactory(new PropertyValueFactory<>("toplamMiktar"));
        colDeneyAciklama.setCellValueFactory(new PropertyValueFactory<>("aciklama"));

        loadDeneyler();

        txtDeneyAra.textProperty().addListener((obs, oldVal, newVal) -> {
            applyDeneyFilter();
        });


    }

    private void loadMalzemelerFromCSV() {

        masterList = FXCollections.observableArrayList(
                UserCSVUtil.readMalzemeler()
        );

        filteredList = new FilteredList<>(masterList, p -> true);
        tblMalzemeler.setItems(filteredList);

        applyAutoFilter();
    }

    private void applyAutoFilter() {

        String searchText = txtAra.getText() == null
                ? ""
                : txtAra.getText().toLowerCase().trim();

        filteredList.setPredicate(malzeme -> {

            // ==== TEXT FİLTRE ====
            boolean textMatch =
                    searchText.isEmpty()
                            || malzeme.getAd().toLowerCase().contains(searchText)
                            || malzeme.getTur().toLowerCase().contains(searchText)
                            || malzeme.getDurum().toLowerCase().contains(searchText)
                            || (searchText.equals("kritik") && malzeme.getStok() <= 2)
                            || (searchText.equals("aktif")
                            && malzeme.getDurum().equalsIgnoreCase("aktif"));

            // ==== FILTRELE KRİTİK OLANLAR CHECKBOX====
            boolean kritikMatch =
                    !chkKritik.isSelected()
                            || malzeme.getStok() <= 2;

            return textMatch && kritikMatch;
        });
    }

    @FXML
    private void onMalzemeEkle() {

        Dialog<Malzeme> dialog = new Dialog<>();
        dialog.setTitle("Malzeme Ekle");

        ButtonType kaydetBtn = new ButtonType("Kaydet", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(kaydetBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtAd = new TextField();
        TextField txtTur = new TextField();
        TextField txtStok = new TextField();
        TextField txtDurum = new TextField();

        grid.addRow(0, new Label("Malzeme Adı:"), txtAd);
        grid.addRow(1, new Label("Kategori:"), txtTur);
        grid.addRow(2, new Label("Stok:"), txtStok);
        grid.addRow(3, new Label("Durum (AKTIF/KRITIK):"), txtDurum);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == kaydetBtn) {

                String ad = txtAd.getText().trim();
                String tur = txtTur.getText().trim();
                String stokStr = txtStok.getText().trim();
                String durum = txtDurum.getText().trim().toUpperCase();

                // ==== BOŞ ALAN KONTROLÜ ====
                if (ad.isEmpty() || tur.isEmpty() || stokStr.isEmpty() || durum.isEmpty()) {
                    showError("Lütfen tüm alanları doldurun.");
                    return null;
                }

                // ==== STOK SAYI MI ====
                int stok;
                try {
                    stok = Integer.parseInt(stokStr);
                } catch (NumberFormatException e) {
                    showError("Stok sayısal bir değer olmalıdır.");
                    return null;
                }

                // ==== DURUM GEÇERLİ Mİ ===-
                if (!durum.equals("AKTIF") && !durum.equals("KRITIK")) {
                    showError("Durum sadece AKTIF veya KRITIK olabilir.");
                    return null;
                }

                return new Malzeme(
                        ad,
                        tur,
                        stok,
                        durum,
                        LocalDate.now().toString()
                );
            }
            return null;
        });


        dialog.showAndWait().ifPresent(malzeme -> {
            masterList.add(malzeme);
            UserCSVUtil.writeAll(masterList);

            // LOG BURAYA
            UserCSVUtil.logIslem("Ekleme", malzeme.getAd());

            loadMalzemelerFromCSV();
            loadSonIslemler();
            loadDashboardData();

            loadKategoriGrafik();

            showInfo(
                    "Başarılı",
                    "Malzeme başarıyla eklendi."
            );
        });


        loadDashboardData();


    }

    @FXML
    private void onMalzemeGuncelle() {

        Malzeme secili = tblMalzemeler.getSelectionModel().getSelectedItem();

        if (secili == null) {
            showError("Lütfen güncellenecek malzemeyi seçin.");
            return;
        }

        Dialog<Malzeme> dialog = new Dialog<>();
        dialog.setTitle("Malzeme Güncelle");

        ButtonType kaydetBtn = new ButtonType("Güncelle", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(kaydetBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtAd = new TextField(secili.getAd());
        txtAd.setDisable(true);

        TextField txtTur = new TextField(secili.getTur());
        txtTur.setDisable(true);

        TextField txtStok = new TextField();
        TextField txtDurum = new TextField();

        grid.addRow(0, new Label("Malzeme Adı:"), txtAd);
        grid.addRow(1, new Label("Kategori:"), txtTur);
        grid.addRow(2, new Label("Yeni Stok:"), txtStok);
        grid.addRow(3, new Label("Durum (AKTIF/KRITIK):"), txtDurum);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == kaydetBtn) {

                String stokStr = txtStok.getText().trim();
                String durum = txtDurum.getText().trim().toUpperCase();

                // === BOŞ ALAN KONTROLÜ ===
                if (stokStr.isEmpty() || durum.isEmpty()) {
                    showError("Lütfen tüm alanları doldurun.");
                    return null;
                }

                // ==== STOK SAYI MI ====
                int stok;
                try {
                    stok = Integer.parseInt(stokStr);
                } catch (NumberFormatException e) {
                    showError("Stok sayısal bir değer olmalıdır.");
                    return null;
                }

                // === DURUM GEÇERLİ Mİ ===
                if (!durum.equals("AKTIF") && !durum.equals("KRITIK")) {
                    showError("Durum sadece AKTIF veya KRITIK olabilir.");
                    return null;
                }

                return new Malzeme(
                        secili.getAd(),
                        secili.getTur(),
                        stok,
                        durum,
                        LocalDate.now().toString()
                );
            }
            return null;
        });


        dialog.showAndWait().ifPresent(yeni -> {
            masterList.remove(secili);
            masterList.add(yeni);
            UserCSVUtil.writeAll(masterList);

            // LOG BURAYA
            UserCSVUtil.logIslem("Güncelleme", yeni.getAd());

            loadMalzemelerFromCSV();
            loadSonIslemler();
            loadDashboardData();

            loadKategoriGrafik();

            showInfo(
                    "Başarılı",
                    "Malzeme başarıyla güncellendi."
            );
        });
    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Hata");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void handleSil() {

        Malzeme selected =
                tblMalzemeler.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Hata", "Lütfen silinecek bir malzeme seçin.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Silme Onayı");
        confirm.setHeaderText("Malzeme Silinsin mi?");
        confirm.setContentText(selected.getAd() + " silinecek.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL)
                != ButtonType.OK) {
            return;
        }

        masterList.remove(selected);
        UserCSVUtil.writeAll(masterList);

        // LOG BURAYA
        UserCSVUtil.logIslem("Silme", selected.getAd());

        loadMalzemelerFromCSV();
        loadSonIslemler();
        loadDashboardData();

        loadKategoriGrafik();

        showInfo(
                "Silindi",
                "Malzeme başarıyla silindi."
        );

        loadMalzemelerFromCSV();

        loadDashboardData();
    }

    @FXML
    private void onSepeteEkle() {

        Malzeme m = tblMalzemeler.getSelectionModel().getSelectedItem();
        if (m == null) {
            showError("Lütfen bir malzeme seçin");
            return;
        }

        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Miktar Gir");
        dialog.setHeaderText(m.getAd());
        dialog.setContentText("Kullanılacak miktar:");

        dialog.showAndWait().ifPresent(val -> {

            int miktar;
            try {
                miktar = Integer.parseInt(val);
            } catch (NumberFormatException e) {
                showError("Lütfen sayısal bir değer girin");
                return;
            }

            if (miktar <= 0) {
                showError("Miktar 0'dan büyük olmalı");
                return;
            }

            // SEPETTEKİ MEVCUT MİKTARI HESAPLA
            int sepetteki = sepetList.stream()
                    .filter(s -> s.getAd().equals(m.getAd()))
                    .mapToInt(SepetItem::getMiktar)
                    .sum();

//            if (sepetteki + miktar > m.getStok()) {
//                showError("Yetersiz stok");
//                return;
//            }
            int kalanStok = m.getStok() - sepetteki;

            if (miktar > kalanStok) {
                showError("Yetersiz stok");
                return;
            }

            // SEPETTE VAR MI
            SepetItem mevcut = null;
            for (SepetItem s : sepetList) {
                if (s.getAd().equals(m.getAd())) {
                    mevcut = s;
                    break;
                }
            }

            if (mevcut != null) {
                mevcut.setMiktar(mevcut.getMiktar() + miktar);
            } else {
                sepetList.add(new SepetItem(m, miktar));
            }

            tblSepet.refresh();
            UserCSVUtil.writeSepet(sepetList);

            // İŞLEM KAYDI
            UserCSVUtil.logIslem("Sepete Ekleme", m.getAd());

            // ANA SAYFA → SON İŞLEMLERİ YENİLE
            loadSonIslemler();

            showInfo("Başarılı", "Malzeme sepete eklendi");
        });
        loadSepetOzet();
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void loadDashboardData() {

        ObservableList<Malzeme> list =
                FXCollections.observableArrayList(
                        UserCSVUtil.readMalzemeler()
                );

        // TOPLAM
        lblToplam.setText(String.valueOf(list.size()));

        // KRİTİK
        long kritikSayisi = list.stream()
                .filter(m -> m.getStok() <= 2)
                .count();
        lblKritik.setText(String.valueOf(kritikSayisi));

        // TÜRLER
        long turSayisi = list.stream()
                .map(Malzeme::getTur)
                .distinct()
                .count();
        lblTur.setText(String.valueOf(turSayisi));

        // UYARILAR
        uyariListView.getItems().clear();
        list.stream()
                .filter(m -> m.getStok() <= 2)
                .forEach(m ->
                        uyariListView.getItems().add(
                                "⚠ " + m.getAd() + " (Stok: " + m.getStok() + ")"
                        )
                );
    }

    private void kategoriGrafikYukle() {
        Map<String, Integer> kategoriSayilari = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/malzemeler.csv")))) {

            String line;
            boolean ilkSatir = true;

            while ((line = br.readLine()) != null) {
                if (ilkSatir) {
                    ilkSatir = false;
                    continue;
                }

                String[] data = line.split(",");
                String kategori = data[2];

                kategoriSayilari.put(
                        kategori,
                        kategoriSayilari.getOrDefault(kategori, 0) + 1
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : kategoriSayilari.entrySet()) {
            pieChartData.add(
                    new PieChart.Data(entry.getKey(), entry.getValue())
            );
        }

        kategoriPieChart.setData(pieChartData);
        kategoriPieChart.setLegendVisible(true);
        kategoriPieChart.setLabelsVisible(true);
    }

    private void loadKategoriGrafik() {

        var malzemeler = UserCSVUtil.readMalzemeler();

        Map<String, Integer> kategoriSayac = new HashMap<>();

        for (Malzeme m : malzemeler) {
            kategoriSayac.merge(m.getTur(), 1, Integer::sum);
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        kategoriSayac.forEach((kategori, adet) -> {
            pieData.add(new PieChart.Data(kategori, adet));
        });

        kategoriPieChart.setData(pieData);
        kategoriPieChart.setLegendVisible(true);
        //kategoriPieChart.setLabelsVisible(false);

        for (PieChart.Data data : kategoriPieChart.getData()) {
            Tooltip tooltip = new Tooltip(
                    data.getName() + " : " + (int) data.getPieValue() + " adet"
            );
            Tooltip.install(data.getNode(), tooltip);
        }

    }

    private void loadSonIslemler() {

        ObservableList<SonIslem> list =
                FXCollections.observableArrayList(
                        UserCSVUtil.readSonIslemler()
                );

        sonIslemlerTable.setItems(list);
    }

    // ===== SEPET =====
    @FXML private TableView<SepetItem> tblSepet;

    @FXML private TableColumn<SepetItem, String> colSepetAd;
    @FXML private TableColumn<SepetItem, String> colSepetKategori;
    @FXML private TableColumn<SepetItem, Integer> colSepetMiktar;
    @FXML private TableColumn<SepetItem, Integer> colSepetStok;
    @FXML private TableColumn<SepetItem, String> colSepetDurum;

    private ObservableList<SepetItem> sepetList =
            FXCollections.observableArrayList();

    @FXML private TextField txtSepetAra;
    @FXML private CheckBox chkYetersiz;

    private FilteredList<SepetItem> filteredSepet;


    private void loadSepet() {
        sepetList.clear();
        sepetList.addAll(UserCSVUtil.readSepet());
        applySepetFilter();
    }


    @FXML
    private void onDeneyOnayla() {
        if (masterList == null) {
            masterList = FXCollections.observableArrayList(
                    UserCSVUtil.readMalzemeler()
            );
        }

        if (sepetList.isEmpty()) {
            showError("Sepet boş");
            return;
        }

        TextInputDialog aciklamaDialog = new TextInputDialog();
        aciklamaDialog.setTitle("Deney Açıklaması");
        aciklamaDialog.setHeaderText("Deney açıklaması giriniz");
        aciklamaDialog.setContentText("Açıklama:");

        aciklamaDialog.showAndWait().ifPresent(aciklama -> {

            if (aciklama.trim().isEmpty()) {
                showError("Açıklama boş olamaz");
                return;
            }

            String deneyId = "D-" + (100000 + new Random().nextInt(900000));
            String tarih = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            );

            int toplamMiktar = sepetList.stream()
                    .mapToInt(SepetItem::getMiktar)
                    .sum();

            // ==== DENEYLER.CSV ====
            UserCSVUtil.writeDeney(
                    deneyId,
                    tarih,
                    sepetList.size(),
                    toplamMiktar,
                    aciklama
            );

            // ==== DENEY_DETAY.CSV ====
            for (SepetItem s : sepetList) {
                UserCSVUtil.writeDeneyDetay(
                        deneyId,
                        s.getAd(),
                        s.getKategori(),
                        s.getMiktar()
                );
            }

            // ==== STOK DÜŞ ====
            for (SepetItem s : sepetList) {
                for (Malzeme m : masterList) {
                    if (m.getAd().equals(s.getAd())) {
                        m.setStok(m.getStok() - s.getMiktar());
                    }
                }
            }

            UserCSVUtil.writeAll(masterList);

            sepetList.clear();
            UserCSVUtil.writeSepet(sepetList);

            loadSepet();
            loadSepetOzet();
            loadMalzemelerFromCSV();
            loadDeneyler();

            showInfo("Başarılı", "Deney oluşturuldu");
        });
    }



    @FXML
    private void onSepettenCikar() {
        SepetItem s = tblSepet.getSelectionModel().getSelectedItem();
        if (s != null) {
            sepetList.remove(s);
            UserCSVUtil.writeSepet(sepetList);
        }
        loadSepetOzet();
    }

    @FXML
    private void onSepetiTemizle() {
        sepetList.clear();
        UserCSVUtil.writeSepet(sepetList);
        loadSepetOzet();
    }

    private void applySepetFilter() {

        String search = txtSepetAra.getText() == null
                ? ""
                : txtSepetAra.getText().toLowerCase().trim();

        filteredSepet.setPredicate(item -> {

            boolean textMatch =
                    search.isEmpty()
                            || item.getAd().toLowerCase().contains(search)
                            || item.getKategori().toLowerCase().contains(search);

            boolean yetersizMatch =
                    !chkYetersiz.isSelected()
                            || item.getMiktar() > item.getStok();

            return textMatch && yetersizMatch;
        });
    }

    private void loadSepetOzet() {
        lblSepet.setText(String.valueOf(sepetList.size()));
    }

    @FXML
    private void onSifreDegistir() {

        String eski = txtEskiSifre.getText();
        String yeni = txtYeniSifre.getText();
        String tekrar = txtYeniSifreTekrar.getText();

        if (eski.isEmpty() || yeni.isEmpty() || tekrar.isEmpty()) {
            showError("Tüm alanları doldurun");
            return;
        }

        if (!yeni.equals(tekrar)) {
            showError("Yeni şifreler uyuşmuyor");
            return;
        }

        try {
            boolean ok = AuthCSVUtil.changePassword(
                    Session.currentUser,
                    eski,
                    yeni
            );

            if (!ok) {
                showError("Eski şifrenizi yanlış girdiniz");
                return;
            }

            showInfo("Başarılı", "Şifre başarıyla değiştirildi");

            txtEskiSifre.clear();
            txtYeniSifre.clear();
            txtYeniSifreTekrar.clear();

        } catch (Exception e) {
            showError("Şifre değiştirme hatası");
        }
    }

    @FXML
    private void onCikisYap() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Çıkış");
        alert.setHeaderText("Çıkış yapmak istiyor musunuz?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                Stage stage = (Stage) lblSepet.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/javaprojegui/view/Login.fxml")
                );
                stage.setScene(new Scene(loader.load(), 360, 420));
                stage.setTitle("Giriş Yap");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Session.clear();
    }

    @FXML
    private void loadDeneyler() {

        deneyMasterList = FXCollections.observableArrayList(
                UserCSVUtil.readDeneyler()
        );

        deneyFilteredList = new FilteredList<>(deneyMasterList, p -> true);
        tblDeneyler.setItems(deneyFilteredList);

        applyDeneyFilter();
    }

    private void applyDeneyFilter() {

        if (deneyFilteredList == null) return;

        String search = txtDeneyAra.getText() == null
                ? ""
                : txtDeneyAra.getText().toLowerCase().trim();

        deneyFilteredList.setPredicate(deney -> {

            if (search.isEmpty()) return true;

            return deney.getAciklama() != null
                    && deney.getAciklama().toLowerCase().contains(search);
        });
    }



    @FXML
    private void onDeneyDetay() {

        Deney secili = tblDeneyler.getSelectionModel().getSelectedItem();

        if (secili == null) {
            showError("Lütfen bir deney seçin");
            return;
        }

        List<String> detaylar =
                UserCSVUtil.readDeneyDetay(secili.getDeneyId());

        if (detaylar.isEmpty()) {
            showInfo("Bilgi", "Bu deneye ait detay bulunamadı");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deney Detayları");
        alert.setHeaderText("Deney: " + secili.getDeneyId());

        TextArea area = new TextArea(String.join("\n", detaylar));
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefWidth(400);
        area.setPrefHeight(300);

        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }



    @FXML
    private void onDeneySil() {

        Deney secili = tblDeneyler.getSelectionModel().getSelectedItem();

        if (secili == null) {
            showError("Lütfen bir deney seçin");
            return;
        }

        UserCSVUtil.deleteDeney(secili.getDeneyId());
        loadDeneyler();

        showInfo("Silindi", "Deney silindi");
    }
}