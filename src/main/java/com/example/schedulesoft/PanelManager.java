package com.example.schedulesoft;

import com.example.schedulesoft.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


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

            Locale locale = new Locale("fr");
            ResourceBundle rb = ResourceBundle.getBundle("com.example.schedulesoft.UI", locale);

            Node child = FXMLLoader.load(Objects.requireNonNull(PanelManager.class.getResource(panel.getFileName())), rb);

            HBox.setHgrow(child, Priority.ALWAYS);
            VBox.setVgrow(child, Priority.ALWAYS);

            panelContainer.getChildren().clear();
            panelContainer.getChildren().add(child);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
