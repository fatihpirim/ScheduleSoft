package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.util.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(View.Login.getFileName())), AppConfig.getResourceBundle());
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