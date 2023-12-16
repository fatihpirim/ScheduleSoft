package com.example.schedulesoft.controller;

import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.domain.Contact;
import com.example.schedulesoft.model.AppointmentModel;
import com.example.schedulesoft.service.AppointmentService;
import com.example.schedulesoft.service.ContactService;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.PanelManager;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.util.LocaleUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentTableController implements Initializable {

    @FXML
    Label zoneIdLabel;

    @FXML
    ResourceBundle resources;

    @FXML
    TableView<Appointment> appointmentTable;

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button adjustButton;

    @FXML
    private TableColumn<Appointment, Number> idCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> startCol;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> endCol;
    @FXML
    private TableColumn<Appointment, Number> customerIdCol;
    @FXML
    private TableColumn<Appointment, Number> userIdCol;

    private final AppointmentService appointmentService = new AppointmentService();
    private final AppointmentModel appointmentModel = AppointmentModel.getInstance();

    private final ContactService contactService = new ContactService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());

        setCellValueFactoryOfColumns();

        appointmentModel.setAppointments(appointmentService.getAllAppointments());

        appointmentTable.setItems(appointmentModel.getAppointments());

        editButton.setVisible(false);
        deleteButton.setVisible(false);
        adjustButton.setVisible(false);

        appointmentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {

            // cool use of ternary operator
            System.out.println("Currently selected: " +  ((newSelection != null) ? newSelection.getId() + " " + newSelection.getType() : "N/A") + ". Previously selected: " +
                    ((oldSelection != null) ? oldSelection.getId() + " " + oldSelection.getType() : "N/A"));

            appointmentModel.setSelectedAppointment(newSelection);

            editButton.setVisible(true);
            deleteButton.setVisible(true);
            adjustButton.setVisible(true);
        });

        System.out.println(appointmentModel.getAppointments());

        setCellFactoryOfColumns();

    }

    @FXML
    private void onAdd() {
        System.out.println("Clicked Add (appointment)");

        appointmentModel.setSelectedAppointment(null);

        PanelManager.changePanelTo(View.AppointmentForm);
    }

    @FXML
    private void onEdit() {
        System.out.println("Clicked Edit (appointment)");

        PanelManager.changePanelTo(View.AppointmentForm);
    }

    @FXML
    private void onDelete() {
        System.out.println("Clicked Delete (appointment)");

        Appointment appointment = appointmentModel.getSelectedAppointment();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");

        String headerText = "Are you sure you want to delete this appointment?";
        String contentText = appointment.getTitle() + " (" + formatter.format(appointment.getStartDateTime()) + " - " +
                formatter.format(appointment.getEndDateTime()) + ") ";

        Optional<ButtonType> confirmation = showConfirmationAlert(headerText, contentText);

        if(confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            System.out.println("Deleting " + contentText);
            boolean appointmentDeleted = appointmentService.deleteAppointment(appointment);
            if(appointmentDeleted) {
                appointmentModel.removeSelectedCustomer();
            }
        } else if(confirmation.isPresent() && confirmation.get() == ButtonType.CANCEL) {
            System.out.println("Cancelled deletion");
        }

    }

    @FXML
    private void onAdjust(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/schedulesoft/view/AdjustTimeDialog.fxml"), AppConfig.getResourceBundle());

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        locationCol.setCellValueFactory(appointment -> {
            String location = appointment.getValue().getLocation();
            return new SimpleStringProperty(location);
        });

        contactCol.setCellValueFactory(appointment -> {
            int contactId = appointment.getValue().getContactId();
            Contact contact = contactService.getContactById(contactId);
            return new SimpleStringProperty(contact.getName());
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

        userIdCol.setCellValueFactory(appointment -> {
            int userIdCol = appointment.getValue().getUserId();
            return new SimpleIntegerProperty(userIdCol);
        });
    }

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

    private Optional<ButtonType> showConfirmationAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

}
