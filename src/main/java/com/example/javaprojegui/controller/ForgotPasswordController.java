package com.example.javaprojegui.controller;

import com.example.javaprojegui.util.AuthCSVUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ForgotPasswordController {

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtNewPasswordAgain;
    @FXML private Label lblMessage;

    @FXML
    private void handleReset() {

        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String p1 = txtNewPassword.getText();
        String p2 = txtNewPasswordAgain.getText();

        if (username.isEmpty() || email.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
            lblMessage.setText("Tüm alanları doldurun!");
            return;
        }

        if (!p1.equals(p2)) {
            lblMessage.setText("Şifreler eşleşmiyor!");
            return;
        }

        try {
            boolean success = AuthCSVUtil.resetPassword(username, email, p1);

            if (success) {
                lblMessage.setStyle("-fx-text-fill: green;");
                lblMessage.setText("Şifre güncellendi!");
                goToLogin();
            } else {
                lblMessage.setText("Kullanıcı adı veya email yanlış!");
            }

        } catch (Exception e) {
            lblMessage.setText("CSV hatası!");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojegui/view/Login.fxml")
            );
            stage.setScene(new Scene(loader.load(), 400, 350));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}