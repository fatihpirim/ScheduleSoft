package com.example.schedulesoft.util;

import com.example.schedulesoft.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Objects;


public class PanelManager {

    /**
     * Pane containing the panel
     */
    private static Pane panelContainer;
    private static Node panel;

    /**
     *
     * Sets the panel container
     *
     * @param panelContainer the panel container
     */
    public static void setParent(Pane panelContainer) {
        PanelManager.panelContainer = panelContainer;
    }

    /**
     * Gets the panel
     * @return the panel
     */
    public static Node getPanel() {
        return panel;
    }

    /**
     * Changes panel
     * <p>
     * Prevents setting panel if no parent / panel container has been set.
     * Clears panel container and adds the panel as a child.
     * </p>
     * @param panel new panel (customer table, customer form, dashboard, etc.)
     */
    public static void changePanelTo(View panel) {

        if(PanelManager.panelContainer == null) {
            System.out.println("No panel container (parent node) is set. Cannot set panel/view (child node).");
            return;
        }

        try {

            Node child = FXMLLoader.load(Objects.requireNonNull(PanelManager.class.getResource(panel.getFileName())), AppConfig.getResourceBundle());

            HBox.setHgrow(child, Priority.ALWAYS);
            VBox.setVgrow(child, Priority.ALWAYS);

            panelContainer.getChildren().clear();
            panelContainer.getChildren().add(child);

            PanelManager.panel = child;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the panel container
     */
    public static Pane getPanelContainer() {
        return panelContainer;
    }
}
