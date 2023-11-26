package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Objects;


public class ViewChanger {

    private static Pane parent;
    private static View currentView;

    public static void setParent(Pane parent) {
        ViewChanger.parent = parent;
    }

    public static void changeViewTo(View view) {

        if(ViewChanger.parent == null) {
            System.out.println("No parent is set. Cannot set child node (view).");
            return;
        }

        if(ViewChanger.currentView != null && ViewChanger.currentView.equals(view)) {
            System.out.println("Same view. No change.");
            return;
        }

        try {

            Node child = FXMLLoader.load(Objects.requireNonNull(ViewChanger.class.getResource(view.getFileName())));

            HBox.setHgrow(child, Priority.ALWAYS);
            VBox.setVgrow(child, Priority.ALWAYS);

            parent.getChildren().clear();
            parent.getChildren().add(child);

            ViewChanger.currentView = view;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
