package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.util.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// https://www.baeldung.com/java-dto-pattern

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/Home.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setMinWidth(1100);
        stage.setMinHeight(800);

        stage.setWidth(1100);
        stage.setHeight(800);

        stage.setScene(scene);
        stage.show();

        System.out.println("Height:" + stage.getHeight());
    }

    public static void main(String[] args) {
        Database.getConnection();
        launch();
        Database.closeConnection();
    }
}