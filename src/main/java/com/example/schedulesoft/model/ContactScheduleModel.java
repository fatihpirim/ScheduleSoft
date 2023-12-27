package com.example.schedulesoft.model;

import com.example.schedulesoft.domain.Appointment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * The purpose of this class is to integrate persisted appointment data with the contact schedule table.
 * It is also a local, temporary copy of appointment data in heap
 * <br>
 * Ex: For example, selecting an appointment in table and having a reference to the selected appointment
 * when the view is switched from appointment table to the appointment form
 * <br>
 * This class is implemented using Singleton pattern.
 */
public class ContactScheduleModel {

    /**
     * Appointment currently selected (in table)
     */
    private static final ContactScheduleModel instance = new ContactScheduleModel();

    /**
     * Local copy of all appointments
     */
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();


    private ContactScheduleModel() {
        // Private constructor to enforce singleton pattern
    }

    public static ContactScheduleModel getInstance() {
        return instance;
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



}
