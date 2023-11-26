package com.example.schedulesoft.controller;

import com.example.schedulesoft.ViewChanger;
import com.example.schedulesoft.enums.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    HBox homeScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ViewChanger.setParent(homeScreen);
        ViewChanger.changeViewTo(View.CustomerTable);
    }
}
