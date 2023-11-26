package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Objects;


public class ViewChanger {

    private static Pane parent;

    public static void setParent(Pane parent) {
        ViewChanger.parent = parent;
    }

    public static void changeViewTo(View view) {

        if(ViewChanger.parent == null) {
            System.out.println("No parent is set. Cannot set child node (view).");
            return;
        }

        try {

            Node child = FXMLLoader.load(Objects.requireNonNull(ViewChanger.class.getResource(view.getFileName())));

            HBox.setHgrow(child, Priority.ALWAYS);
            VBox.setVgrow(child, Priority.ALWAYS);

            parent.getChildren().clear();
            parent.getChildren().add(child);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
