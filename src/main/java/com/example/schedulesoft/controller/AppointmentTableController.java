package com.example.schedulesoft.controller;

import com.example.schedulesoft.ViewChanger;
import com.example.schedulesoft.enums.View;
import javafx.fxml.FXML;

public class AppointmentTableController {

    @FXML
    private void onAdd() {
        System.out.println("Clicked Add (appointment)");

        ViewChanger.changeViewTo(View.AppointmentForm);
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
