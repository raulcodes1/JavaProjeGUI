module com.example.javaprojegui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.javaprojegui to javafx.fxml;
    opens com.example.javaprojegui.model to javafx.base;
    exports com.example.javaprojegui;
    exports com.example.javaprojegui.controller;
    opens com.example.javaprojegui.controller to javafx.fxml;
}