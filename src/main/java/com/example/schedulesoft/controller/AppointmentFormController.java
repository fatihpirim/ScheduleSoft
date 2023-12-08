package com.example.schedulesoft.controller;

import com.example.schedulesoft.PanelManager;
import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.domain.Contact;
import com.example.schedulesoft.domain.Customer;
import com.example.schedulesoft.domain.User;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        disableWeekendOnDatePicker(startDatePicker);
        disableWeekendOnDatePicker(endDatePicker);

        Appointment selectedAppointment = appointmentModel.getSelectedAppointment();

        if(appointmentIsSelected) {

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

        } else {

        }

        setHourOptions(startHourComboBox);
        setMinuteOptions(startMinuteComboBox);

        setHourOptions(endHourComboBox);
        setMinuteOptions(endMinuteComboBox);



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

    private void disableWeekendOnDatePicker(DatePicker datePicker) {

        datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        });
    }

    private void setHourOptions(ComboBox<String> hourComboBox ) {
        hourComboBox.setItems(Schedule.getBusinessHours("08:00", "22:00").stream()
                // convert offset-time (business hour) to a zoned-date-time at users time zone
                .map(time -> time.atDate(java.time.LocalDate.now()).atZoneSameInstant(ZoneId.systemDefault()))
                // convert zoned-date-time (business hour) into a formatted string
                .map(offsetTime -> offsetTime.format(DateTimeFormatter.ofPattern("H")))
                // collect into a list
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
    }

    private void setMinuteOptions(ComboBox<String> minuteComboBox) {
        minuteComboBox.getItems().addAll("00", "15", "30", "45");
    }
}
