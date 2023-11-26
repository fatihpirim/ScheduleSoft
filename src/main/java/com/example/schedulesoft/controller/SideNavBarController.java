package com.example.schedulesoft.controller;

import com.example.schedulesoft.ViewChanger;
import com.example.schedulesoft.enums.View;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class SideNavBarController implements Initializable {

    @FXML
    Button customersMenuItem;
    @FXML
    Button appointmentsMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onCustomersClick(Event event) {
        System.out.println("Clicked Customers");

        ViewChanger.changeViewTo(View.CustomerTable);
    }

    @FXML
    private void onAppointmentsClick(Event event) {
        System.out.println("Clicked Appointments");

        ViewChanger.changeViewTo(View.AppointmentTable);
    }
}
