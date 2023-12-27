package com.example.schedulesoft.model;

import com.example.schedulesoft.domain.Appointment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * The purpose of this class is to integrate persisted appointment data with javafx classes.
 * It is also a local, temporary copy of appointment data in heap
 * <br>
 * Ex: For example, selecting an appointment in table and having a reference to the selected appointment
 * when the view is switched from appointment table to the appointment form
 * <br>
 * This class is implemented using Singleton pattern.
 */
public class AppointmentModel {

    private static final AppointmentModel instance = new AppointmentModel();

    /**
     * Appointment currently selected (in table)
     */
    private final ObjectProperty<Appointment> selectedAppointment = new SimpleObjectProperty<>(null);

    /**
     * Local copy of all appointments
     */
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private AppointmentModel() {
        // Private constructor to enforce singleton pattern
    }

    public static AppointmentModel getInstance() {
        return instance;
    }

    /**
     * @return selected appointment
     */
    public Appointment getSelectedAppointment() {
        return selectedAppointment.get();
    }

    public ObjectProperty<Appointment> selectedAppointmentProperty() {
        return selectedAppointment;
    }

    /**
     * @param selectedAppointment new selected appointment
     */
    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment.set(selectedAppointment);
    }

    /**
     * @return get all appointments
     */
    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * @param appointments appointments to set all appointments to
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * @param appointments appointments to set all appointments to
     */
    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = FXCollections.observableArrayList(appointments);
    }

    /**
     * @return unselect the selected customer
     */
    public boolean removeSelectedCustomer() {

        Appointment appointment = getSelectedAppointment();
        setSelectedAppointment(null);
        return appointments.remove(appointment);
    }
}
