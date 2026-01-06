package com.example.javaprojegui.util;

import javafx.scene.control.*;

public class DiyalogUtil {

    public static boolean onayAl(String baslik, String mesaj) {
        ButtonType evet = new ButtonType("Evet", ButtonBar.ButtonData.OK_DONE);
        ButtonType hayir = new ButtonType("Hayır", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.getButtonTypes().setAll(evet, hayir);

        return alert.showAndWait().orElse(hayir) == evet;
    }

    public static void uyari(String baslik, String mesaj) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}