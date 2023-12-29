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

/**
 * Controller managing the interactions between the adjust time dialog box (view) and the appointment model and table (back-end)
 */
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

    /**
     * Holds a reference to whether an appointment is currently selected in the model
     */
    private final boolean appointmentIsSelected = appointmentModel.getSelectedAppointment() != null;

    /**
     * Initializes the controller
     * <p>
     * contains listeners which listen to changes in the start and end date properties (respectively)
     * the listeners handle events using a lambda expression
     * the lambda is used because it provides concise syntax for handling events
     * </p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        this.resources = resources;

        titleLabel.setText(appointmentModel.getSelectedAppointment().getTitle());
        populateFields();

        startTimeComboBox.setDisable(false);
        endTimeComboBox.setDisable(false);

        disableWeekends(startDatePicker);
        startDatePicker.setEditable(false);

        // lambda is used
        startDatePicker.valueProperty().addListener((observable, oldDate, newDate) -> {
            setStartTimeItems();
            setEndTimeItems();
            startTimeComboBox.setDisable(false);
            endTimeComboBox.setDisable(false);
        });

        // lambda is used
        startTimeComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setEndTimeItems());
        // lambda is used
        endTimeComboBox.valueProperty().addListener((observable, oldTime, newTime) -> setStartTimeItems());

        formatTimeComboBoxItems(startTimeComboBox);
        formatTimeComboBoxItems(endTimeComboBox);

    }

    /**
     * Saves appointment with new adjusted time to database
     *
     * @param event save button is clicked
     */
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
                showErrorAlert("Error saving appointment time.\nField empty or time is overlapping");
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

    /**
     * Cancels the adjust time request and closes dialog window
     * @param event cancel button is clicked
     */
    @FXML
    private void onCancel(Event event) {
        System.out.println("Clicked cancel");

        closeDialog();
    }

    /**
     * Populates all fields with current, existing data
     * <p>
     *     a stream with a lambda expression is used to convert ZonedDateTime objects to the user's time zone and collect them in a list
     *     a lambda, rather than a for loop, is used to preform these operations because it is more concise
     * </p>
     */
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

    /**
     * Disables the weekends on a date picker ui
     * <p>
     *  A lambda is used to create a DateCell because it is more concise
     * </p>
     * @param datePicker date picker in which in the weekends are being disables
     */
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

    /**
     * Gets the start times that are available (to be selected) based on the current selected end time
     * <p>
     *     A lambda is used to convert to user's time zone and filter the available end times
     *     It is used because it makes the code more concise
     * </p>
     */
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

    /**
     * Gets the end times that are available (to be selected) based on the current selected start time
     * <p>
     *     A lambda is used to convert to user's time zone and filter the available start times
     *     It is used because it makes the code more concise
     * </p>
     */
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

    /**
     * Formats the items (date times objects) in the combo box
     * @param timeComboBox combo box being formatted
     */
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

    /**
     * Shows an error dialog
     * @param errorMessage message to be displayed in the error dialog box
     */
    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please fix the following errors:");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    /**
     * Closes the adjust time dialog box
     */
    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }



}
