package com.example.javaprojegui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/javaprojegui/view/Login.fxml")
        );

        Scene scene = new Scene(loader.load(), 400, 300);

        scene.getStylesheets().add(
                getClass().getResource("/com/example/javaprojegui/view/style/app.css").toExternalForm()
        );

        System.out.println(
                getClass().getResource("/com/example/javaprojegui/view/style/app.css")
        );


        stage.setTitle("Giriş Yap");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
