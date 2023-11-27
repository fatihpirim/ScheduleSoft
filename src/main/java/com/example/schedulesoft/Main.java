package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.util.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

// https://www.baeldung.com/java-dto-pattern

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Locale locale = new Locale("en");
        ResourceBundle rb = ResourceBundle.getBundle("com.example.schedulesoft.UI", locale);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(View.Login.getFileName())), rb);
        Scene scene = new Scene(root);

        PageManager.setPageContainer(scene);

        stage.setMinWidth(1100);
        stage.setMinHeight(800);

        stage.setWidth(1100);
        stage.setHeight(800);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Database.getConnection();
        launch();
        Database.closeConnection();
    }
}