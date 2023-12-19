package com.example.schedulesoft.util;

import javafx.scene.Parent;
import javafx.scene.Scene;

import com.example.schedulesoft.enums.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class PageManager {

    private static Scene pageContainer;

    public static void setPageContainer(Scene pageContainer) {
        PageManager.pageContainer = pageContainer;
    }

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
