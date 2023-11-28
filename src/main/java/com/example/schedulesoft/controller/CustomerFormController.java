package com.example.schedulesoft.controller;

import com.example.schedulesoft.AppConfig;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    Label zoneIdLabel;

    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());
    }

    @FXML
    private void onSave(Event event) {
        System.out.println("Clicked Save");
    }

    @FXML
    private void onCancel(Event event) {
        System.out.println("Clicked Cancel");
    }
}
