package com.example.schedulesoft.controller;

import com.example.schedulesoft.PanelManager;
import com.example.schedulesoft.domain.*;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.model.AppointmentModel;
import com.example.schedulesoft.model.CustomerModel;
import com.example.schedulesoft.service.*;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.util.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AppointmentFormController implements Initializable {

    @FXML
    Label zoneIdLabel;

    @FXML
    ResourceBundle resources;

    @FXML
    TextField titleField;
    @FXML
    TextField descriptionField;
    @FXML
    TextField locationField;
    @FXML
    TextField typeField;
    @FXML
    ComboBox<Contact> contactComboBox;
    @FXML
    DatePicker startDatePicker;
    @FXML
    ComboBox<String> startHourComboBox;
    @FXML
    ComboBox<String> startMinuteComboBox;
    @FXML
    DatePicker endDatePicker;
    @FXML
    ComboBox<String> endHourComboBox;
    @FXML
    ComboBox<String> endMinuteComboBox;
    @FXML
    ComboBox<Integer> customerIdComboBox;
    @FXML
    ComboBox<Integer> userIdComboBox;
    @FXML
    TextField appointmentIdField;

    private final AppointmentService appointmentService = new AppointmentService();
    private final AppointmentModel appointmentModel = AppointmentModel.getInstance();

    private final boolean appointmentIsSelected = appointmentModel.getSelectedAppointment() != null;

    private final ContactService contactService = new ContactService();
    private final CustomerService customerService = new CustomerService();
    private final UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());

        if(appointmentIsSelected) {

            populateFields();

        } else {

        }

        // Fetch contacts from database and set as the options in the contact-combo-box
        contactComboBox.setItems(contactService.getAllContacts().stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));

        // Disables the dates before the minimum date in the appointment start-date date picker
        disableDatesAfterMaxDate(startDatePicker, endDatePicker.getValue());

        // Disables the dates after the maximum date in the appointment start-date date picker
        disableDatesBeforeMinDate(endDatePicker, startDatePicker.getValue());

        // Listens to changes in the appointment start time and disables dates accordingly
        startDatePicker.valueProperty().addListener((observable, oldDate, newDate) -> disableDatesBeforeMinDate(endDatePicker, startDatePicker.getValue()));

        // Listens to changes in the appointment end time and disables dates accordingly
        endDatePicker.valueProperty().addListener((observable, oldDate, newDate) -> disableDatesAfterMaxDate(startDatePicker, endDatePicker.getValue()));

        // Sets the items in the start hour combo box
        setStartHourOptions();

        // Listens to changes in start hour and updates end hour options
        startHourComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setEndHourOptions());

        // Sets the items in the start minute combo box
        setStartMinuteOptions();

        // Listens to change in start minute and updates end minute options
        startMinuteComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setEndMinuteOptions());

        // Sets the items in the end hour combo box
        setEndHourOptions();

        // Listens to changes in end hour
        endHourComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setStartHourOptions());

        // Sets the items in the END minute combo box
        setEndMinuteOptions();

        // Listens to change in start minute and updates end minute options
        endMinuteComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setStartMinuteOptions());

        customerIdComboBox.setItems(customerService.getAllCustomers().stream()
                .map(Customer::getId)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));

        userIdComboBox.setItems(userService.getAllUsers().stream()
                .map(User::getId)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));


    }

    @FXML
    private void onSave(Event event) {
        System.out.println("Clicked Save");
    }

    @FXML
    private void onCancel(Event event) {
        System.out.println("Clicked Cancel");

        appointmentModel.setSelectedAppointment(null);
        PanelManager.changePanelTo(View.AppointmentTable);

    }

    private void populateFields() {

        Appointment selectedAppointment = appointmentModel.getSelectedAppointment();

        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        typeField.setText(selectedAppointment.getType());

        Contact contact = contactService.getContactById(selectedAppointment.getContactId());
        contactComboBox.setValue(contact);

        ZonedDateTime startDateTime = selectedAppointment.getStartDateTime().withZoneSameInstant(ZoneId.systemDefault());
        LocalDate startDate = startDateTime.toLocalDate();
        startDatePicker.setValue(startDate);
        startHourComboBox.setValue(startDateTime.format(DateTimeFormatter.ofPattern("H")));

        startMinuteComboBox.setValue(startDateTime.format(DateTimeFormatter.ofPattern("mm")));

        ZonedDateTime endDateTime = selectedAppointment.getEndDateTime().withZoneSameInstant(ZoneId.systemDefault());
        LocalDate endDate = endDateTime.toLocalDate();
        endDatePicker.setValue(endDate);
        endHourComboBox.setValue(endDateTime.format(DateTimeFormatter.ofPattern("H")));
        endMinuteComboBox.setValue(endDateTime.format(DateTimeFormatter.ofPattern("mm")));

        customerIdComboBox.setValue(selectedAppointment.getCustomerId());
        userIdComboBox.setValue(selectedAppointment.getUserId());

        appointmentIdField.setText(String.valueOf(selectedAppointment.getId()));
    }

    private void disableDatesBeforeMinDate(DatePicker datePicker, LocalDate minDate) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(minDate) || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    setDisable(true);
                    setStyle("-fx-background-color: #D3D3D380;");
                }
            }
        });
    }

    private void disableDatesAfterMaxDate(DatePicker datePicker, LocalDate maxDate) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isAfter(maxDate) || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    setDisable(true);
                    setStyle("-fx-background-color: #D3D3D380;");
                }
            }
        });
    }

    private void setStartHourOptions() {
        startHourComboBox.setItems(Schedule.getBusinessHours("08:00", "22:00").stream()
                // convert offset-time (business hour) to a zoned-date-time at users time zone
                .map(ot -> ot.atDate(java.time.LocalDate.now()).atZoneSameInstant(ZoneId.systemDefault()))
                // convert zoned-date-time (business hour) into a formatted string
                .map(zdt -> zdt.format(DateTimeFormatter.ofPattern("H")))
                // filter through only hours that are after before end hour
                .filter(startStr -> endHourComboBox.getValue() == null || Integer.parseInt(startStr) <= Integer.parseInt(endHourComboBox.getValue()))
                // collect into a list
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
    }

    private void setEndHourOptions() {
        endHourComboBox.setItems(Schedule.getBusinessHours("08:00", "22:00").stream()
                // convert offset-time (business hour) to a zoned-date-time at users time zone
                .map(ot -> ot.atDate(java.time.LocalDate.now()).atZoneSameInstant(ZoneId.systemDefault()))
                // convert zoned-date-time (business hour) into a formatted string
                .map(zdt -> zdt.format(DateTimeFormatter.ofPattern("H")))
                // filter through only hours that are after start hour
                .filter(endStr -> startHourComboBox.getValue() == null || Integer.parseInt(endStr) >= Integer.parseInt(startHourComboBox.getValue()))
                // collect into a list
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
    }

    private void setStartMinuteOptions() {
        List<String> minutes = Arrays.asList("00", "15", "30", "45");
        startMinuteComboBox.setItems(minutes.stream()
                .filter(startMin -> endMinuteComboBox.getValue() == null || Integer.parseInt(startMin) <= Integer.parseInt(endMinuteComboBox.getValue()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
    }

    private void setEndMinuteOptions() {
        List<String> minutes = Arrays.asList("00", "15", "30", "45");
        endMinuteComboBox.setItems(minutes.stream()
                .filter(endMin -> startMinuteComboBox.getValue() == null || Integer.parseInt(endMin) >= Integer.parseInt(startMinuteComboBox.getValue()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
    }

    private void setMinuteOptions(ComboBox<String> minuteComboBox) {
        minuteComboBox.getItems().addAll();
    }


}

