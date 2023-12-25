package com.example.schedulesoft.controller;

import com.example.schedulesoft.util.PanelManager;
import com.example.schedulesoft.auth.SessionHolder;
import com.example.schedulesoft.domain.*;
import com.example.schedulesoft.enums.Severity;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.model.AppointmentModel;
import com.example.schedulesoft.service.*;
import com.example.schedulesoft.ui.Toast;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.util.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.*;
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
    Label appointmentIdLabel;
    @FXML
    TextField appointmentIdField;

    @FXML
    Button saveButton;

    private final AppointmentService appointmentService = new AppointmentService();
    private final AppointmentModel appointmentModel = AppointmentModel.getInstance();

    private final boolean appointmentIsSelected = appointmentModel.getSelectedAppointment() != null;

    private final ContactService contactService = new ContactService();
    private final CustomerService customerService = new CustomerService();
    private final UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        this.resources = resources;

        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());

        // An appointment is selected (Edit)
        if(appointmentIsSelected) {

            populateFields();

            startTimeComboBox.setDisable(false);
            endTimeComboBox.setDisable(false);

            appointmentIdLabel.setVisible(true);
            appointmentIdField.setVisible(true);

        // An appointment is not selected (Add)
        } else {
            startTimeComboBox.setDisable(true);
            endTimeComboBox.setDisable(true);

            appointmentIdLabel.setVisible(false);
            appointmentIdField.setVisible(false);
        }

        // Fetch contacts from database and sets the items of the contact-combo-box
        contactComboBox.setItems(contactService.getAllContacts().stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));

        // Configure appointment date picker
        disableWeekends(startDatePicker);
        startDatePicker.setEditable(false);

        // Update time options when date is changed
        startDatePicker.valueProperty().addListener((observable, oldDate, newDate) -> {
            setStartTimeItems();
            setEndTimeItems();
            startTimeComboBox.setDisable(false);
            endTimeComboBox.setDisable(false);
        });

        // Change time combo box items so that a start-time after an end-time is never available in combo box
        startTimeComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setEndTimeItems());
        endTimeComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setStartTimeItems());

        // Format ZonedDateTime items to display as MM/dd HH:mm
        formatTimeComboBoxItems(startTimeComboBox);
        formatTimeComboBoxItems(endTimeComboBox);

        // Fetch customers from database and sets the items of customer-id-combo-box to customers' ids
        customerIdComboBox.setItems(customerService.getAllCustomers().stream()
                .map(Customer::getId)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));

        // Fetch users from database and sets the items of user-id-combo-box to users' ids
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

        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        ZonedDateTime startDateTime = startTimeComboBox.getValue();
        ZonedDateTime endDateTime = endTimeComboBox.getValue();
        ZonedDateTime createdOn = ZonedDateTime.now();
        String createdBy = SessionHolder.getInstance().getSession().getUser().getUsername();
        ZonedDateTime lastUpdated = ZonedDateTime.now();
        String lastUpdatedBy = SessionHolder.getInstance().getSession().getUser().getUsername();
        int customerId = customerIdComboBox.getValue();
        int userId = userIdComboBox.getValue();
        int contactId = contactComboBox.getValue().getId();

        boolean appointmentIsSaved = false;

        if(appointmentIsSelected) {
            System.out.println("UPDATING APPOINTMENT");
            Appointment selectedAppointment = appointmentModel.getSelectedAppointment();
            selectedAppointment.setTitle(title);
            selectedAppointment.setDescription(description);
            selectedAppointment.setLocation(location);
            selectedAppointment.setType(type);
            selectedAppointment.setStartDateTime(startDateTime);
            selectedAppointment.setEndDateTime(endDateTime);
            selectedAppointment.setLastUpdated(lastUpdated);
            selectedAppointment.setLastUpdatedBy(lastUpdatedBy);
            selectedAppointment.setCustomerId(customerId);
            selectedAppointment.setUserId(userId);
            selectedAppointment.setContactId(contactId);

            try {
                appointmentIsSaved = appointmentService.saveAppointment(selectedAppointment);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                showErrorAlert(e.getMessage());
            }
        } else {
            System.out.println("ADDING APPOINTMENT");
            Appointment newAppointment = new Appointment(title, description, location, type, startDateTime,
                    endDateTime, createdOn, createdBy, lastUpdated, lastUpdatedBy, customerId,
                    userId, contactId);
            try {
                appointmentIsSaved = appointmentService.saveAppointment(newAppointment);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                showErrorAlert(e.getMessage());
            }
        }

        if(appointmentIsSaved) {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            Toast toast = new Toast(resources.getString("success"), resources.getString("saved_appointment"), Severity.SUCCESS);
            toast.show(stage);

            PanelManager.changePanelTo(View.AppointmentTable);

            System.out.println("Saved Successfully.");
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
        // Determine the business hours the start and end datetime are a part of to handle some edge cases
        ObservableList<ZonedDateTime> businessHoursOnDayBefore = Schedule.getBusinessHours(startDateTime.toLocalDate().minusDays(1),"08:00", "22:00", "America/New_York").stream()
                .map(zdt -> zdt.withZoneSameInstant(ZoneId.systemDefault()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        if(businessHoursOnDayBefore.contains(startDateTime) || businessHoursOnDayBefore.contains(endDateTime)) {
            startDate = startDateTime.toLocalDate().minusDays(1);
        }
        startDatePicker.setValue(startDate);

        startTimeComboBox.setValue(startDateTime);
        endTimeComboBox.setValue(endDateTime);
        setStartTimeItems();
        setEndTimeItems();

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

    private void setStartTimeItems() {
        LocalDate selectedDate = startDatePicker.getValue();

        if(selectedDate == null) {
            System.out.println("ERROR: Date not selected");
            return;
        }
        ObservableList<ZonedDateTime> times = Schedule.getBusinessHours(selectedDate,"08:00", "22:00", "America/New_York").stream()
                .map(zdt -> zdt.withZoneSameInstant(ZoneId.systemDefault()))
                .filter(zdt -> endTimeComboBox.getValue() == null || zdt.isBefore(endTimeComboBox.getValue()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        startTimeComboBox.setItems(times);


    }

    private void setEndTimeItems() {
        LocalDate selectedDate = startDatePicker.getValue();

        if(selectedDate == null) {
            System.out.println("ERROR: Date not selected");
            return;
        }
        ObservableList<ZonedDateTime> times = Schedule.getBusinessHours(selectedDate,"08:00", "22:00", "America/New_York").stream()
                .map(zdt -> zdt.withZoneSameInstant(ZoneId.systemDefault()))
                .filter(zdt -> startTimeComboBox.getValue() == null || zdt.isAfter(startTimeComboBox.getValue()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        endTimeComboBox.setItems(times);
    }

    private void formatTimeComboBoxItems(ComboBox<ZonedDateTime> timeComboBox) {
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

    // Validators

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

    private void validateCustomerId() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(customerIdComboBox.getValue() == null) {
            errorMessage.append("Customer ID is empty\n");
        }
        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validateUserId() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(userIdComboBox.getValue() == null) {
            errorMessage.append("User ID is empty\n");
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

        try {
            validateCustomerId();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }
        try {
            validateUserId();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        if(!errorMessage.toString().isEmpty()) {
            System.out.println(errorMessage);
            showErrorAlert(errorMessage.toString());
        }

        return errorMessage.toString().isEmpty();
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please fix the following errors:");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }


}

