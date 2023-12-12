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
import javafx.util.StringConverter;

import java.net.URL;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    ComboBox<ZonedDateTime> startTimeComboBox;
    @FXML
    ComboBox<ZonedDateTime> endTimeComboBox;
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
            startTimeComboBox.setDisable(true);
            endTimeComboBox.setDisable(true);
        }

        // Fetch contacts from database and set as the options in the contact-combo-box
        contactComboBox.setItems(contactService.getAllContacts().stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));

        disableWeekends(startDatePicker);
        startDatePicker.setEditable(false);

        startDatePicker.valueProperty().addListener((observable, oldDate, newDate) -> {
            setTimeItems(startTimeComboBox);
            setTimeItems(endTimeComboBox);
            startTimeComboBox.setDisable(false);
            endTimeComboBox.setDisable(false);
        });

        // Sets the items in the customer id combo box
        customerIdComboBox.setItems(customerService.getAllCustomers().stream()
                .map(Customer::getId)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));

        // Sets the items in the user id combo box
        userIdComboBox.setItems(userService.getAllUsers().stream()
                .map(User::getId)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));

    }

    @FXML
    private void onSave(Event event) {
        System.out.println("Clicked Save");

        boolean allFormsAreValid = validateAllForms();
        if(!allFormsAreValid) {
            return;
        }
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
        ZonedDateTime endDateTime = selectedAppointment.getEndDateTime().withZoneSameInstant(ZoneId.systemDefault());

        LocalDate startDate = startDateTime.toLocalDate();
        startDatePicker.setValue(startDate);

        startTimeComboBox.setValue(startDateTime);
        endTimeComboBox.setValue(endDateTime);

        startTimeComboBox.setDisable(false);
        endTimeComboBox.setDisable(false);

        setTimeItems(startTimeComboBox);
        setTimeItems(endTimeComboBox);

        customerIdComboBox.setValue(selectedAppointment.getCustomerId());
        userIdComboBox.setValue(selectedAppointment.getUserId());

        appointmentIdField.setText(String.valueOf(selectedAppointment.getId()));
    }

    private void disableWeekends(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if ((date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #D3D3D380;");
                }
            }
        });
    }

    private void setTimeItems(ComboBox<ZonedDateTime> timeComboBox) {
        ObservableList<ZonedDateTime> times = Schedule.getBusinessHours("08:00", "22:00").stream()
                .map(ot -> ot.atDate(startDatePicker.getValue()).atZoneSameInstant(ZoneId.systemDefault()))//bug is here somewhere
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        System.out.println(times);

        timeComboBox.setItems(times);

        timeComboBox.setConverter(new StringConverter<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");

            @Override
            public String toString(ZonedDateTime zonedDateTime) {
                return zonedDateTime != null ? formatter.format(zonedDateTime) : "";
            }

            @Override
            public ZonedDateTime fromString(String string) {
                return ZonedDateTime.parse(string, formatter);
            }
        });
    }



    private void validateTextField(TextField textField, String textFieldName) throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(textField.getText().length() > 49) {
            errorMessage.append(textFieldName).append(" is too long\n");
        }
        if(textField.getText().isEmpty()) {
            errorMessage.append(textFieldName).append(" is empty\n");
        }

        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validateContact() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(contactComboBox.getValue() == null) {
            errorMessage.append("Contact is empty\n");
        }
        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validateDate(DatePicker datePicker, String datePickerName) throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(datePicker.getValue() == null) {
            errorMessage.append(datePickerName).append(" is empty\n");
        }
        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validateTime() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(startTimeComboBox.getValue() == null ) {
            errorMessage.append("Start time is empty\n");
        }
        if (endTimeComboBox.getValue() == null) {
            errorMessage.append("End time is empty\n");
        }
        if(startTimeComboBox.getValue() != null && endTimeComboBox.getValue() != null) {
            if(startTimeComboBox.getValue().isAfter(endTimeComboBox.getValue())) {
                errorMessage.append("Invalid start and end time. The start time is after end time\n");
            }
        }
        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }



    private boolean validateAllForms() {

        StringBuilder errorMessage = new StringBuilder();

        try {
            validateTextField(titleField, "Title");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateTextField(descriptionField, "Description");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateTextField(locationField, "Location");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateTextField(typeField, "Type");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateContact();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateDate(startDatePicker, "Start Date");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }


        try {
            validateTime();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        System.out.println(errorMessage);

        return errorMessage.toString().isEmpty();
    }



}

