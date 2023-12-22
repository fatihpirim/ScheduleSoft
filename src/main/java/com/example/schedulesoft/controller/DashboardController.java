package com.example.schedulesoft.controller;

import com.example.schedulesoft.ui.AppointmentsByMonthChart;
import com.example.schedulesoft.ui.AppointmentsByTypeChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    ComboBox<String> chartComboBox;

    @FXML
    VBox chartContainer;

    @FXML
    ScrollPane loginActivityScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Chart Control & UI
        ObservableList<String> chartComboBoxItems = FXCollections.observableArrayList(Arrays.asList("By Month", "By Type"));
        chartComboBox.setItems(chartComboBoxItems);
        chartComboBox.setValue("By Month");

        AppointmentsByMonthChart appointmentByMonthChart = new AppointmentsByMonthChart();
        AppointmentsByTypeChart appointmentByTypeChart = new AppointmentsByTypeChart();

        chartContainer.getChildren().clear();
        chartContainer.getChildren().add(appointmentByMonthChart.create());

        chartComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("By Month")) {
                chartContainer.getChildren().clear();
                chartContainer.getChildren().add(appointmentByMonthChart.create());
            } else if(newValue.equals("By Type")) {
                chartContainer.getChildren().clear();
                chartContainer.getChildren().add(appointmentByTypeChart.create());
            }
        });

        // Login Activity Control & UI
        TextFlow loginActivityTextFlow = new TextFlow();
        loginActivityTextFlow.setPadding(new Insets(5, 5, 5, 5));
        String filePath = System.getProperty("user.dir")+"/login_activity.txt";

        try {
            // Read the contents of the text file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                Text textNode = new Text(line + "\n");
                loginActivityTextFlow.getChildren().add(textNode);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginActivityScrollPane.setContent(loginActivityTextFlow);


    }


}
