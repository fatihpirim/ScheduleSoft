package com.example.schedulesoft.controller;

import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.service.CustomerService;
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

    private final CustomerService customerService = new CustomerService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());
    }

    @FXML
    private void onSave(Event event) {
        System.out.println("Clicked Save");

        // check if all forms are valid
        // customerService.save()
    }

    @FXML
    private void onCancel(Event event) {
        System.out.println("Clicked Cancel");

        // Throw warning "are you sure you want to exit without saving?"
        // Change view to customer table
    }
}
