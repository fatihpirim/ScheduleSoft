package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.util.Database;
import com.example.schedulesoft.util.PageManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * Course: Software II C195
 * @author Muzaffer Fatih Pirim
 */
public class Main extends Application {
    /**
     *
     * Sets the default View to the Login page.
     * Configures dimension limits of the app window.
     * Kills all processes if window is closed.
     * Sets language of app to system language
     *
     * @param stage The primary stage for the application
     * @throws IOException Throws I/O Exception if the FXMLLoader cannot find resource
     */
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

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Entry point for the app.
     *
     * <p>
     * Opens database connection.
     * Launches app.
     * Closes database connection when the application thread is killed.
     * </p>
     *
     * @param args N/A
     */
    public static void main(String[] args) {
        Database.getConnection();
        launch();
        Database.closeConnection();
    }
}