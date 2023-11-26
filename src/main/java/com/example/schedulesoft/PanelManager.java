package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Objects;


public class PanelManager {

    private static Pane panelContainer;

    public static void setParent(Pane panelContainer) {
        PanelManager.panelContainer = panelContainer;
    }

    public static void changePanelTo(View panel) {

        if(PanelManager.panelContainer == null) {
            System.out.println("No panel container (parent node) is set. Cannot set panel/view (child node).");
            return;
        }

        try {

            Node child = FXMLLoader.load(Objects.requireNonNull(PanelManager.class.getResource(panel.getFileName())));

            HBox.setHgrow(child, Priority.ALWAYS);
            VBox.setVgrow(child, Priority.ALWAYS);

            panelContainer.getChildren().clear();
            panelContainer.getChildren().add(child);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
