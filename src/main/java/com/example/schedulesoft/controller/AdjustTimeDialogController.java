package com.example.schedulesoft.controller;

import com.example.schedulesoft.util.PanelManager;
import com.example.schedulesoft.auth.SessionHolder;
import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.enums.Message;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.event.DAOEvent;
import com.example.schedulesoft.model.AppointmentModel;
import com.example.schedulesoft.service.AppointmentService;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdjustTimeDialogController implements Initializable {

    @FXML
    ResourceBundle resources;

    @FXML
    Label titleLabel;
    @FXML
    DatePicker startDatePicker;
    @FXML
    ComboBox<ZonedDateTime> startTimeComboBox;
    @FXML
    ComboBox<ZonedDateTime> endTimeComboBox;

    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;

    private final AppointmentService appointmentService = new AppointmentService();
    private final AppointmentModel appointmentModel = AppointmentModel.getInstance();

    private final boolean appointmentIsSelected = appointmentModel.getSelectedAppointment() != null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        titleLabel.setText(appointmentModel.getSelectedAppointment().getTitle());
        populateFields();

        startTimeComboBox.setDisable(false);
        endTimeComboBox.setDisable(false);

        disableWeekends(startDatePicker);
        startDatePicker.setEditable(false);

        startDatePicker.valueProperty().addListener((observable, oldDate, newDate) -> {
            setStartTimeItems();
            setEndTimeItems();
            startTimeComboBox.setDisable(false);
            endTimeComboBox.setDisable(false);
        });

        startTimeComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setEndTimeItems());
        endTimeComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setStartTimeItems());

        formatTimeComboBoxItems(startTimeComboBox);
        formatTimeComboBoxItems(endTimeComboBox);

    }

    @FXML
    private void onSave(Event event) {
        System.out.println("Clicked save");

        ZonedDateTime startDateTime = startTimeComboBox.getValue();
        ZonedDateTime endDateTime = endTimeComboBox.getValue();
        ZonedDateTime lastUpdated = ZonedDateTime.now();
        String lastUpdatedBy = SessionHolder.getInstance().getSession().getUser().getUsername();

        if(appointmentIsSelected) {
            System.out.println("UPDATING APPOINTMENT");

            Appointment selectedAppointment = appointmentModel.getSelectedAppointment();
            selectedAppointment.setStartDateTime(startDateTime);
            selectedAppointment.setEndDateTime(endDateTime);
            selectedAppointment.setLastUpdated(lastUpdated);
            selectedAppointment.setLastUpdatedBy(lastUpdatedBy);

            boolean appointmentIsSaved = false;

            try {
                appointmentIsSaved = appointmentService.saveAppointment(selectedAppointment);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                showErrorAlert(e.getMessage());
            }

            if(appointmentIsSaved) {
                System.out.println("Saved Successfully.");
                closeDialog();
                PanelManager.changePanelTo(View.AppointmentTable);
                Event daoEvent = new DAOEvent(Message.SUCCESS);
                saveButton.fireEvent(daoEvent);
            }

        } else {
            System.out.println("Unable to save. Appointment is not selected.");
        }
    }

    @FXML
    private void onCancel(Event event) {
        System.out.println("Clicked cancel");

        closeDialog();
    }

    private void populateFields() {

        Appointment selectedAppointment = appointmentModel.getSelectedAppointment();

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

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please fix the following errors:");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }



}
