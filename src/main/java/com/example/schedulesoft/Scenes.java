package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

// Instead of switching Scenes, This class allows us to switch a root node on an already existing Scene

public class Scenes {

    private static Scene scene;

    public static void setScene(Scene scene) {
        Scenes.scene = scene;
    }

    public static void changeViewTo(View view) {

        if(Scenes.scene == null) {
            System.out.println("No scene is set. Cannot set root node (view).");
            return;
        }

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Scenes.class.getResource(view.getFileName())));

            Scenes.scene.setRoot(root); // set root node
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
