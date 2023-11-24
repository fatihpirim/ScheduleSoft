package com.example.schedulesoft.controller;

import com.example.schedulesoft.Main;
import com.example.schedulesoft.Scenes;
import com.example.schedulesoft.enums.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideNavBarController implements Initializable {

    @FXML
    private ListView<String> menuListView;

    private final ObservableList<String> ITEMS = FXCollections.observableArrayList ("Customers", "Appointments");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        menuListView.setItems(ITEMS);

        menuListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {



            if(newSelection.equals("Customers")) {
//                Scenes.changeViewTo(View.CustomerTable);
                System.out.println("Changed view to customer table");
            } else if (newSelection.equals("Appointments")) {
//                Scenes.changeViewTo(View.AppointmentTable);
                System.out.println("Changed view to appointment table");
            }


        });


    }


}
