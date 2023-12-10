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
        startHourComboBox.valueProperty().addListener((observable, oldTime, newTime) -> {
            // String selectedEndMinute = endMinuteComboBox.getValue();
            setEndHourOptions();
            // endMinuteComboBox.setValue(selectedEndMinute)
            setStartMinuteOptions();
        });

        // Sets the items in the start minute combo box
        setStartMinuteOptions();

        // Listens to change in start minute and updates end minute options
        startMinuteComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setEndMinuteOptions());

        // Sets the items in the end hour combo box
        setEndHourOptions();

        // Listens to changes in end hour
        endHourComboBox.valueProperty().addListener((observable, oldTime, newTime) -> {
            String selectedStartMinute = startMinuteComboBox.getValue();
            setStartHourOptions();
            startMinuteComboBox.setValue(selectedStartMinute);
            // setEndMinuteOptions();
        });

        // Sets the items in the end minute combo box
        setEndMinuteOptions();

        // Listens to change in end minute and updates start minute options
        endMinuteComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setStartMinuteOptions());

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
                if ((minDate != null && date.isBefore(minDate)) || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
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
                if ((maxDate != null && date.isAfter(maxDate)) || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
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
        ObservableList<String> minutes = FXCollections.observableArrayList(Arrays.asList("00", "15", "30", "45"));

        boolean necessaryFieldsAreEmpty = endHourComboBox.getValue() == null || endMinuteComboBox.getValue() == null || startHourComboBox.getValue() == null;

        LocalTime endTime = LocalTime.of(Integer.parseInt(endHourComboBox.getValue() != null ? endHourComboBox.getValue() : "0"),
                Integer.parseInt(endMinuteComboBox.getValue() != null ? endMinuteComboBox.getValue() : "00"));

        startMinuteComboBox.setItems(minutes.stream()
                .filter(minute -> startHourComboBox.getValue() != null)
//                .peek(minute -> System.out.println("EndHOURValue = " + endHourComboBox.getValue() + "\nEndMINUTEValue = " + endMinuteComboBox.getValue()+ "\nStartHOURValue = " + startHourComboBox.getValue()))
                .filter(minute -> (necessaryFieldsAreEmpty) || LocalTime.of(Integer.parseInt(startHourComboBox.getValue()) , Integer.parseInt(minute)).isBefore(endTime) ||
                        LocalTime.of(Integer.parseInt(startHourComboBox.getValue()) , Integer.parseInt(minute)).equals(endTime))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
    }

    private void setEndMinuteOptions() {
        ObservableList<String> minutes = FXCollections.observableArrayList(Arrays.asList("00", "15", "30", "45"));

        endMinuteComboBox.setItems(minutes);
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

    private void validateTime(ComboBox<String> timeComboBox, String timeComboBoxName) throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(timeComboBox.getValue() == null) {
            errorMessage.append(timeComboBoxName).append(" is empty\n");
        }
        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validateAppointmentInterval() throws Exception {
        StringBuilder errorMessage = new StringBuilder();

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
            validateDate(endDatePicker, "End Date");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateTime(startHourComboBox, "Start Hour");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateTime(startMinuteComboBox, "Start Minute");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateTime(endHourComboBox, "End Hour");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateTime(endMinuteComboBox, "End Minute");
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        System.out.println(errorMessage);

        return errorMessage.toString().isEmpty();
    }



}

