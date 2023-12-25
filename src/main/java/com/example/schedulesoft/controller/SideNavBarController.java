package com.example.schedulesoft.controller;

import com.example.schedulesoft.auth.SessionHolder;
import com.example.schedulesoft.util.PageManager;
import com.example.schedulesoft.util.PanelManager;
import com.example.schedulesoft.auth.UserAuth;
import com.example.schedulesoft.enums.View;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class SideNavBarController implements Initializable {

    @FXML
    ResourceBundle resources;

    @FXML
    Label appLogo;
    @FXML
    Label userLabel;
    @FXML
    Button customersMenuItem;
    @FXML
    Button appointmentsMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        this.resources = resources;

        Font font = Font.loadFont(getClass().getResourceAsStream("/com/example/schedulesoft/font/Orbitron/Orbitron-VariableFont_wght.ttf"), 22);
        appLogo.setFont(font);

        userLabel.setText(SessionHolder.getInstance().getSession().getUser().getUsername());
    }

    @FXML
    private void onDashboardClick(Event event) {
        System.out.println("Clicked Dashboard");

        PanelManager.changePanelTo(View.Dashboard);
    }

    @FXML
    private void onCustomersClick(Event event) {
        System.out.println("Clicked Customers");

        PanelManager.changePanelTo(View.CustomerTable);
    }

    @FXML
    private void onAppointmentsClick(Event event) {
        System.out.println("Clicked Appointments");

        PanelManager.changePanelTo(View.AppointmentTable);
    }

    @FXML
    private void onLogout(Event event) throws Exception {
        System.out.println("Clicked logout");

        boolean userIsDeauthenticated = UserAuth.deauthenticate();

        if (userIsDeauthenticated) {
            PageManager.changePageTo(View.Login);
        }

    }


}
