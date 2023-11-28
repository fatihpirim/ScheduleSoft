package com.example.schedulesoft.controller;

import com.example.schedulesoft.AppConfig;
import com.example.schedulesoft.PanelManager;
import com.example.schedulesoft.enums.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentTableController implements Initializable {

    @FXML
    Label zoneIdLabel;

    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());
    }

    @FXML
    private void onAdd() {
        System.out.println("Clicked Add (appointment)");

        PanelManager.changePanelTo(View.AppointmentForm);
    }

    @FXML
    private void onEdit() {

    }

    @FXML
    private void onDelete() {

    }

    @FXML
    private void onAdjust() {

    }
}
