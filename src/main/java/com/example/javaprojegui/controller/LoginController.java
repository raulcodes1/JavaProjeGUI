package com.example.javaprojegui.controller;

import com.example.javaprojegui.util.AuthCSVUtil;
import com.example.javaprojegui.model.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMessage;

    @FXML
    private void handleLogin() {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Kullanıcı adı ve şifre boş olamaz!");
            return;
        }

        try {
            // 🔥 TEK METOT
            String role = AuthCSVUtil.authenticate(username, password);

            if (role == null) {
                lblMessage.setText("Kullanıcı adı veya şifre hatalı!");
                return;
            }

            // SESSION
            Session.currentUser = username;
            Session.currentRole = role;

            if (role.equals("ADMIN")) {
                openAdminPage();
            } else {
                openUserPage();
            }

        } catch (Exception e) {
            lblMessage.setText("Dosya okuma hatası!");
            e.printStackTrace();
        }
    }

    private void openAdminPage() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojegui/view/Admin_Sayfasi.fxml")
            );
            stage.setScene(new Scene(loader.load(), 900, 600));
            stage.setTitle("Admin Paneli");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openUserPage() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojegui/view/KullaniciSayfasi.fxml")
            );
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.setTitle("Kullanıcı Paneli");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openRegister() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojegui/view/Register.fxml")
            );
            stage.setScene(new Scene(loader.load(), 400, 400));
            stage.setTitle("Kayıt Ol");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openForgotPassword() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojegui/view/ForgotPassword.fxml")
            );
            stage.setScene(new Scene(loader.load(), 400, 420));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
