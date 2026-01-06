package com.example.javaprojegui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void init() {
        System.out.println("init başladı");
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojegui/view/Login.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Giriş Sistemi");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("Stop başladı");
    }

    public static void main(String[] args) {
        launch();
    }
}