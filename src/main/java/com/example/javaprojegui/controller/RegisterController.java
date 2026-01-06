package com.example.javaprojegui.controller;

import com.example.javaprojegui.util.AuthCSVUtil;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRole;
    @FXML private Label lblMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbRole.getItems().addAll(
                "KULLANICI",
                     "ADMIN"
        );
    }

    @FXML
    private void handleRegister() {

        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String role = cmbRole.getValue();

        // BOŞ ALAN KONTROLÜ
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Tüm alanları doldurmalısınız!");
            return;
        }

        if (role == null) {
            lblMessage.setText("Rol seçmelisiniz!");
            return;
        }

        try {
            // AYNI USERNAME
            if (AuthCSVUtil.usernameExists(username)) {
                lblMessage.setText("Bu kullanıcı adı zaten kayıtlı!");
                return;
            }

            // AYNI EMAIL
            if (AuthCSVUtil.emailExists(email)) {
                lblMessage.setText("Bu e-mail zaten kayıtlı!");
                return;
            }

            // KAYIT
            AuthCSVUtil.saveUser(username, email, password, role);

            lblMessage.setStyle("-fx-text-fill: green;");
            lblMessage.setText("Kayıt başarılı! Giriş yapabilirsiniz.");

        } catch (Exception e) {
            lblMessage.setText("CSV yazma hatası!");
            e.printStackTrace();
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
            stage.setTitle("Giriş Yap");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}