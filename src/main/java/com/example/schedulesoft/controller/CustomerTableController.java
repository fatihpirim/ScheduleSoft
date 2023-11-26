package com.example.schedulesoft.controller;

import com.example.schedulesoft.ViewChanger;
import com.example.schedulesoft.enums.View;
import javafx.fxml.FXML;

public class CustomerTableController {

    @FXML
    private void onAdd() {
        System.out.println("Clicked Add (customer)");

        ViewChanger.changeViewTo(View.CustomerForm);
    }

    @FXML
    private void onEdit() {

    }

    @FXML
    private void onDelete() {

    }
}
