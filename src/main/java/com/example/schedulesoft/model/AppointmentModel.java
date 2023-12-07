package com.example.schedulesoft.model;

import com.example.schedulesoft.domain.Appointment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AppointmentModel {

    private static final AppointmentModel instance = new AppointmentModel();

    private final ObjectProperty<Appointment> selectedAppointment = new SimpleObjectProperty<>(null);

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private AppointmentModel() {
        // Private constructor to enforce singleton pattern
    }

    public static AppointmentModel getInstance() {
        return instance;
    }

    public Appointment getSelectedAppointment() {
        return selectedAppointment.get();
    }

    public ObjectProperty<Appointment> selectedAppointmentProperty() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment.set(selectedAppointment);
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = FXCollections.observableArrayList(appointments);
    }

    public boolean removeSelectedCustomer() {

        Appointment appointment = getSelectedAppointment();
        setSelectedAppointment(null);
        return appointments.remove(appointment);
    }
}
