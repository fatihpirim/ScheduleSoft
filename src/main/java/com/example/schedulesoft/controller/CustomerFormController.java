package com.example.schedulesoft.controller;

import javafx.event.Event;
import javafx.fxml.FXML;

public class CustomerFormController {
    @FXML
    private void onSave(Event event) {
        System.out.println("Clicked Save");
    }

    @FXML
    private void onCancel(Event event) {
        System.out.println("Clicked Cancel");
    }
}
