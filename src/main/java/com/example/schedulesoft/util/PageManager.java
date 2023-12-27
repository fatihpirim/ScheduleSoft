package com.example.schedulesoft.util;

import javafx.scene.Parent;
import javafx.scene.Scene;

import com.example.schedulesoft.enums.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

/**
 * Utility class for changing pages in the application.
 */
public class PageManager {

    /**
     * Scene containing the page (login or home)
     */
    private static Scene pageContainer;

    /**
     *
     * @param pageContainer Scene that will be the container of the page
     */
    public static void setPageContainer(Scene pageContainer) {
        PageManager.pageContainer = pageContainer;
    }

    /**
     *
     * Changes page to the desired View (login or home).
     *
     * <p>
     * Prevents page change if there is no container for the page set.
     * If there is a container, get the View file (fxml) and set it as the root of the page container.
     * </p>
     *
     * @param page the view to change the page to
     */
    public static void changePageTo(View page) {

        if(PageManager.pageContainer== null) {
            System.out.println("No page container (scene) set");
            return;
        }

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(PageManager.class.getResource(page.getFileName())), AppConfig.getResourceBundle());

            PageManager.pageContainer.setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
