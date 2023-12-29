package com.example.schedulesoft.controller;

import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.domain.Contact;
import com.example.schedulesoft.model.ContactScheduleModel;
import com.example.schedulesoft.service.AppointmentService;
import com.example.schedulesoft.service.ContactService;
import com.example.schedulesoft.ui.AppointmentsByCountryChart;
import com.example.schedulesoft.ui.AppointmentsByMonthChart;
import com.example.schedulesoft.ui.AppointmentsByTypeChart;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.util.LocaleUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller managing the interactions between the dashboard panel (view) and various models (back-end)
 */
public class DashboardController implements Initializable {

    @FXML
    ResourceBundle resources;

    @FXML
    Label zoneIdLabel;

    @FXML
    ComboBox<String> chartComboBox;
    @FXML
    VBox chartContainer;

    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    TableView<Appointment> contactScheduleTable;
    @FXML
    private TableColumn<Appointment, Number> idCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> startCol;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> endCol;
    @FXML
    private TableColumn<Appointment, Number> customerIdCol;

    private final AppointmentService appointmentService = new AppointmentService();
    private final ContactScheduleModel contactScheduleModel = ContactScheduleModel.getInstance();

    private final ContactService contactService = new ContactService();

    /**
     * The initialize method does most of the heavy lifting in this class
     * <p>
     * Creates and displays charts with respective data
     * Populates contact schedule table with associated data
     * </p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        this.resources = resources;

        zoneIdLabel.setText(AppConfig.getAppZoneId().toString());

        // Chart Control & UI
        ObservableList<String> chartComboBoxItems = FXCollections.observableArrayList(Arrays.asList(
                resources.getString("by_month"), resources.getString("by_type"), resources.getString("by_country")));
        chartComboBox.setItems(chartComboBoxItems);
        chartComboBox.setValue(resources.getString("by_month"));

        AppointmentsByMonthChart appointmentByMonthChart = new AppointmentsByMonthChart(resources);
        AppointmentsByTypeChart appointmentByTypeChart = new AppointmentsByTypeChart(resources);
        AppointmentsByCountryChart appointmentsByCountryChart = new AppointmentsByCountryChart(resources);

        chartContainer.getChildren().clear();
        chartContainer.getChildren().add(appointmentByMonthChart.create());

        chartComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(resources.getString("by_month"))) {
                chartContainer.getChildren().clear();
                chartContainer.getChildren().add(appointmentByMonthChart.create());
            } else if(newValue.equals(resources.getString("by_type"))) {
                chartContainer.getChildren().clear();
                chartContainer.getChildren().add(appointmentByTypeChart.create());
            } else if(newValue.equals(resources.getString("by_country"))) {
                chartContainer.getChildren().clear();
                chartContainer.getChildren().add(appointmentsByCountryChart.create());
            }
        });


        // contact schedule UI / Control

        ObservableList<Contact> contacts = contactService.getAllContacts().stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        contactComboBox.setItems(contacts);
        contactComboBox.setValue(contacts.get(0));

        contactScheduleModel.setAppointments(appointmentService.getAllAppointments().stream().
                filter(appointment -> appointment.getContactId() == contactComboBox.getValue().getId())
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
        contactScheduleTable.setItems(contactScheduleModel.getAppointments());

        contactComboBox.valueProperty().addListener((observable, oldContact, newContact) -> {
            contactScheduleModel.setAppointments(appointmentService.getAllAppointments().stream().
                    filter(appointment -> appointment.getContactId() == contactComboBox.getValue().getId())
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
            contactScheduleTable.setItems(contactScheduleModel.getAppointments());
        });


        setCellValueFactoryOfColumns();
        setCellFactoryOfColumns();
    }

    /**
     * Sets the cell value factory for the columns in the contact schedule table
     */
    private void setCellValueFactoryOfColumns() {

        idCol.setCellValueFactory(appointment -> {
            int id = appointment.getValue().getId();
            return new SimpleIntegerProperty(id);
        });

        titleCol.setCellValueFactory(appointment -> {
            String title = appointment.getValue().getTitle();
            return new SimpleStringProperty(title);
        });

        descriptionCol.setCellValueFactory(appointment -> {
            String desc = appointment.getValue().getDescription();
            return new SimpleStringProperty(desc);
        });

        typeCol.setCellValueFactory(appointment -> {
            String type = appointment.getValue().getType();
            return new SimpleStringProperty(type);
        });

        startCol.setCellValueFactory(appointment -> {
            ZonedDateTime startZDT = appointment.getValue().getStartDateTime();
            return new SimpleObjectProperty<>(startZDT);
        });

        endCol.setCellValueFactory(appointment -> {
            ZonedDateTime endZDT = appointment.getValue().getEndDateTime();
            return new SimpleObjectProperty<>(endZDT);
        });

        customerIdCol.setCellValueFactory(appointment -> {
            int customerId = appointment.getValue().getCustomerId();
            return new SimpleIntegerProperty(customerId);
        });
    }

    /**
     * Sets the cell factory for the columns in the contact schedule table
     */
    private void setCellFactoryOfColumns() {
        startCol.setCellFactory(tc -> new TableCell<>() {

            @Override
            protected void updateItem(ZonedDateTime startZDT, boolean empty) {
                super.updateItem(startZDT, empty);
                if (empty) {
                    setText(null);
                } else {
                    startZDT = LocaleUtil.changeToTimezone(startZDT); // changes timezone to user's timezone
                    setText(LocaleUtil.formatToLocale(startZDT)); // changes formatting to user's locale (am/pm,24hr time, etc.)
                }
            }
        });

        endCol.setCellFactory(tc -> new TableCell<>() {

            @Override
            protected void updateItem(ZonedDateTime endZDT, boolean empty) {
                super.updateItem(endZDT, empty);
                if (empty) {
                    setText(null);
                } else {
                    endZDT = LocaleUtil.changeToTimezone(endZDT);
                    setText(LocaleUtil.formatToLocale(endZDT));
                }
            }
        });
    }



}
