package com.example.schedulesoft.controller;

import com.example.schedulesoft.PanelManager;
import com.example.schedulesoft.enums.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    HBox panelContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PanelManager.setParent(panelContainer);
        PanelManager.changePanelTo(View.CustomerTable);
    }
}
